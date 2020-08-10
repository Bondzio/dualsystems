package com.dual.bean;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.resteasy.logging.Logger;

import com.dual.entity.ExchangeRate;
import com.dual.entity.ExchangeRate_;

/**
 * Session Bean implementation class ExchangeRateDAO
 */
@Stateless
public class ExchangeRateDAOImpl implements ExchangeRateDAOLocal {

	private static final Logger log = Logger.getLogger(ExchangeRateDAOImpl.class);

	@PersistenceContext
    private EntityManager em;
	
	@EJB
	private MNBClient mnbClient;
	
	private Map<Date, ExchangeRate> cache = new HashMap<>();

	public ExchangeRateDAOImpl() {
    }

	private ExchangeRate create(ExchangeRate exchangeRate) {
		em.persist(exchangeRate);
		return exchangeRate;
	}

	@Override
	public ExchangeRate getById(Long id) {
		return em.find(ExchangeRate.class, id);
	}

	@Override
	public ExchangeRate getByDate(Date date) throws MNBFaultException {
		Date originalQueriedDate = date;
		Date currentQueriedDate = date;
		synchronized (this) {
			ExchangeRate rate = null;
			int attempts = 1;
			final int maxRetries = 30;
			while (rate == null && attempts <= maxRetries + 1) {
				Date standardizedDate = standardizeDate(currentQueriedDate);
				log.info(String.format("Trying to get exchange rate by date, originalQueriedDate: %s, currentQueriedDate: %s, attempt %d", originalQueriedDate, standardizedDate, attempts));
				rate = cache.get(standardizedDate);
				if (rate == null) {
					try {
						log.info("Rate not found in cache, trying to read from DB");
						rate = getByDateFromDb(standardizedDate);
					} catch (NoResultException e) {
						log.info("Rate not found in DB, trying to query from MNB");
						rate = getByDateFromMNB(standardizedDate);
						// possible results:
						// - rate not null: exchange rate returned
						// - rate null: there is no exchange rate for the given date
						// - exception: web service error, throw
						if (rate != null) {
							log.info(String.format("Rate returned from MNB: %s", rate));
							// TODO see comment below
							create(rate);
						}
					}
					if (rate != null) {
						log.info(String.format("Rate added to cache: %s", rate));
						/* TODO example use case: querying exchange rate for 20200810 at 0:30; no rate is available yet,
						 * but there will be one provided at the beginning of the business day, unlike for the weekend for example.
						 * We should persist and cache cases when there is no exchange rate for the day, but only if the day we are
						 * querying for has ended! As soon as we found an exchange rate, we should persist and cache it for the skipped days too
						 */
						cache.put(rate.getDate(), rate);
					} else {
						log.info("Rate not found, trying to find rate for the previous day");
						Calendar cal = Calendar.getInstance();
						cal.setTime(standardizedDate);
						cal.add(Calendar.DATE, -1);
						currentQueriedDate = cal.getTime();
						attempts++;
						// loop until rate is found or we stepped back 30 days
					}
				}
			}
			return rate;
		}
	}
	
	private Date standardizeDate(Date date) {
		try {
			return MNBClient.sdf.parse(MNBClient.sdf.format(date));
		} catch (ParseException e) {
			return null; // can not happen in this case
		}
	}

	private ExchangeRate getByDateFromMNB(Date date) throws MNBFaultException {
		return mnbClient.getExchangeRateByDate(date);
	}

	private ExchangeRate getByDateFromDb(Date date) {
		
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
        Root<ExchangeRate> exchangeRate = cq.from(ExchangeRate.class);
        cq
	        .select(exchangeRate)
	        .where(cb.equal(exchangeRate.get(ExchangeRate_.date), date))
	        .orderBy(cb.desc(exchangeRate.get(ExchangeRate_.date)));
        return em.createQuery(cq).getSingleResult();
	}

	@Override
	public void delete(Long id) {
		em.remove(getById(id));
	}

}
