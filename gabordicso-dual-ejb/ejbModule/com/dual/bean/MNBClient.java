package com.dual.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;

import org.jboss.resteasy.logging.Logger;

import com.dual.entity.ExchangeRate;
import com.dual.mnb.client.generated.MNBArfolyamServiceSoap;
import com.dual.mnb.client.generated.MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage;
import com.dual.mnb.client.generated.MNBArfolyamServiceSoapImpl;

@Stateless
public class MNBClient {

	private static final Logger log = Logger.getLogger(MNBClient.class);
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ExchangeRate getExchangeRateByDate(Date date) throws MNBFaultException {
		String dateStr = sdf.format(date);
		String rateStr = null;
		String response = null;

		MNBArfolyamServiceSoap srv = new MNBArfolyamServiceSoapImpl().getCustomBindingMNBArfolyamServiceSoap();
		try {
			log.info(String.format("Querying MNB for exchange rate on %s", dateStr));
			response = srv.getExchangeRates(dateStr, dateStr, "EUR");
			log.info(String.format("Queried MNB for exchange rate on %s, response: %s", dateStr, response));
			if (response != null) {
				rateStr = parseRateFromResponse(dateStr, response);
			}
		} catch (MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage e) {
			log.error(String.format("Error querying exchange rate for date %s", dateStr), e);
			throw new MNBFaultException(e);
		}

		try {
			double eurExchangeRate = Double.parseDouble(rateStr);
			BigDecimal hufToEurMultiplier = BigDecimal.valueOf(1 / eurExchangeRate);
			ExchangeRate rate = new ExchangeRate();
			rate.setDate(date);
			rate.setHufToEurMultiplier(hufToEurMultiplier);
			return rate;
		} catch (NumberFormatException e) {
			if ("".equals(rateStr)) {
				// MNB response was valid but could not be parsed for an exchange rate,
				// meaning there is no exchange rate for the given day; returning null
				return null;
			}
			String errorMsg = String.format("Unexpected response received from MNB Web Service, dateStr=%s, response=%s", dateStr, response);
			log.error(errorMsg);
			throw new MNBFaultException(errorMsg);
		}
	}
	private String parseRateFromResponse(String dateStr, String response) {
		// FIXME this method is a quick&dirty solution for the task, should use a proper xml parser instead

		// result format: <MNBExchangeRates><Day date="2020-08-07"><Rate unit="1" curr="EUR">346,21</Rate></Day></MNBExchangeRates>
		// can also get <MNBExchangeRates /> as result if there is no exchange rate for the date queried
		
		String prefix = new StringBuilder("<MNBExchangeRates><Day date=\"")
				.append(dateStr)
				.append("\"><Rate unit=\"1\" curr=\"EUR\">")
				.toString();
		String suffix = "</Rate></Day></MNBExchangeRates>";
		String emptyResponse = "<MNBExchangeRates />";

		return response.replace(prefix, "").replace(suffix, "").replace(emptyResponse, "").replace(',', '.');

	}

}
