package com.dual.bean;

import com.dual.entity.InvoiceItem;

public interface InvoiceItemDAO {
	InvoiceItem create(InvoiceItem invoiceItem);
	InvoiceItem getById(Long id);
	InvoiceItem update(InvoiceItem invoiceItem);
	void delete(Long id);

}
