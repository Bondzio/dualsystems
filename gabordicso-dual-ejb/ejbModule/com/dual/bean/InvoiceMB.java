package com.dual.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.transaction.Transactional;

import org.apache.commons.text.StringEscapeUtils;
import org.jboss.resteasy.logging.Logger;

import com.dual.entity.ExchangeRate;
import com.dual.entity.Invoice;
import com.dual.entity.InvoiceItem;

public class InvoiceMB implements Serializable {

	private static final long serialVersionUID = -1197395128755689222L;

	private static final Logger log = Logger.getLogger(InvoiceMB.class);

	private Invoice current = new Invoice();
	private InvoiceItem currentItem = new InvoiceItem();

	public Invoice getCurrent() {
		return current;
	}
	
	public InvoiceItem getCurrentItem() {
		return currentItem;
	}
	
	@EJB
	private InvoiceDAOLocal invoiceDao;

	@EJB
	private InvoiceItemDAOLocal invoiceItemDao;
	
	@EJB
	private ExchangeRateDAOLocal exchangeRateDao;
	
	public InvoiceMB() {
		log.info("InvoiceMB instantiated");
	}

	public List<Invoice> getAll() {
		return invoiceDao.getAll();
	}

	@Transactional
	public void create() {
		log.info("InvoiceMB.create() called");
		sanitizeInvoice(current);
		if (current.getId() == null) {
			List<InvoiceItem> items = current.getItems();
			current.setItems(new ArrayList<>());
			invoiceDao.create(current);
			createItems(items);
			current.setItems(items);
			invoiceDao.update(current);
		}
		resetCurrent();
	}

	private void createItems(List<InvoiceItem> items) {
		if (items != null) {
			for (InvoiceItem item : items) {
				invoiceItemDao.create(item);
			}
		}
	}

	private void sanitizeInvoice(Invoice invoice) {
		String comment = invoice.getComment();
		String customerName = invoice.getCustomerName();
		invoice.setComment(escapeHtml(comment));
		invoice.setCustomerName(escapeHtml(customerName));
		if (invoice.getItems() != null) {
			for (InvoiceItem item : invoice.getItems()) {
				sanitizeItem(item);
			}
		}
	}

	private void sanitizeItem(InvoiceItem item) {
		String productName = item.getProductName();
		String unit = item.getUnit();
		item.setProductName(escapeHtml(productName));
		item.setUnit(escapeHtml(unit));
	}
	
	private String escapeHtml(String s) {
		if (s == null) {
			s = "";
		}
		s = s.trim();
		return s;
//		return StringEscapeUtils.escapeHtml4(s); // FIXME can't get WildFly to find the org.apache.commons.text jars
	}

	public void update() {
		log.info("InvoiceMB.update() called");
		sanitizeInvoice(current);
		if (current.getId() != null) {
			invoiceDao.update(current);
		}
	}
	
	public Invoice getById(Long id) {
		current = invoiceDao.getById(id);
		return current;
	}
	
	public void resetCurrent() {
		log.info("InvoiceMB.reset()");
		current = new Invoice();
		currentItem = new InvoiceItem();
	}
	
	public void delete(Long id) {
		invoiceDao.delete(id);
	}

	public void deleteItem(Long itemId) {
		Invoice viewedInvoice = getViewedInvoice();
		if (itemId != null && viewedInvoice != null && viewedInvoice.getItems() != null) {
			Iterator<InvoiceItem> i = viewedInvoice.getItems().iterator();
			while (i.hasNext()) {
				InvoiceItem item = i.next();
				if (itemId.equals(item.getId())) {
					i.remove();
					invoiceDao.update(viewedInvoice);
					invoiceItemDao.delete(itemId);
					return;
				}
			}
		}
	}
	
	public void addItemToCurrent() {
		if (current.getItems() == null) {
			current.setItems(new ArrayList<>());
		}
		current.getItems().add(currentItem);
		currentItem.setInvoice(current);
		currentItem = new InvoiceItem();
	}
	
	public String hufToEur(Date date, BigDecimal hufAmount) {
		try {
			ExchangeRate exchangeRate = exchangeRateDao.getByDate(date);
			return hufAmount.multiply(exchangeRate.getHufToEurMultiplier()).setScale(2, RoundingMode.HALF_UP).toPlainString();
		} catch (MNBFaultException e) {
			return "Could not query HUF to EUR exchange rate";
		}
	}
	
	public void addTestInvoice() {
		log.info("addTestInvoice() called");
		Invoice invoice = new Invoice();
		invoice.setCustomerName("Customer Name");
		invoice.setIssueDate(new Date());
		invoice.setDueDate(new Date());
		invoiceDao.create(invoice);
		log.info("invoice added, id: " + invoice.getId());
	}


	
	public Long getViewInvoiceId() {
		return viewInvoiceId;
	}

	public void setViewInvoiceId(Long viewInvoiceId) {
		this.viewInvoiceId = viewInvoiceId;
	}

	private Long viewInvoiceId;
	
	public Invoice getViewedInvoice() {
		if (viewInvoiceId == null) {
			return null;
		}
		return invoiceDao.getById(viewInvoiceId); // FIXME cache viewed invoice instance!
	}
	
	public void deleteNewlyCreatedItem(InvoiceItem item) {
		if (item == null || current == null || current.getItems() == null) {
			return;
		}
		current.getItems().remove(item);
	}
	public String formatDate(Date date) {
		return new SimpleDateFormat("yyyy.MM.dd.").format(date); // TODO
	}
}
