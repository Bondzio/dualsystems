package com.dual.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.dual.entity.Invoice;
import com.dual.entity.Invoice_;

/**
 * Session Bean implementation class InvoiceDAO
 */
@Stateless
public class InvoiceDAOImpl implements InvoiceDAOLocal {

	@PersistenceContext
    private EntityManager em;
    /**
     * Default constructor. 
     */
    public InvoiceDAOImpl() {
    }

	@Override
	public Invoice create(Invoice invoice) {
		em.persist(invoice);
		return invoice;
	}

	@Override
	public Invoice getById(Long id) {
		return em.find(Invoice.class, id);
	}

	@Override
	public List<Invoice> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = cb.createQuery(Invoice.class);
        Root<Invoice> invoice = criteria.from(Invoice.class);
        criteria.select(invoice).orderBy(cb.desc(invoice.get(Invoice_.issueDate)));
        return em.createQuery(criteria).getResultList();
	}

	@Override
	public Invoice update(Invoice invoice) {
		em.merge(invoice);
		return invoice;
	}

	@Override
	public void delete(Long id) {
		em.remove(getById(id));
	}

}
