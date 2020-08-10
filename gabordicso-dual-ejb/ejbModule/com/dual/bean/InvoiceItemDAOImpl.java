package com.dual.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dual.entity.InvoiceItem;

@Stateless
public class InvoiceItemDAOImpl implements InvoiceItemDAOLocal {

	@PersistenceContext
    private EntityManager em;
    /**
     * Default constructor. 
     */
    public InvoiceItemDAOImpl() {
    }

    @Override
	public InvoiceItem create(InvoiceItem invoiceItem) {
		em.persist(invoiceItem);
		return invoiceItem;
	}

	@Override
	public InvoiceItem getById(Long id) {
		return em.find(InvoiceItem.class, id);
	}

	@Override
	public InvoiceItem update(InvoiceItem invoiceItem) {
		em.merge(invoiceItem);
		return invoiceItem;
	}

	@Override
	public void delete(Long id) {
		em.remove(getById(id));
	}

}
