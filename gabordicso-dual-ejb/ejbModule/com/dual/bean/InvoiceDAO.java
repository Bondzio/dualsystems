package com.dual.bean;

import java.util.List;

import com.dual.entity.Invoice;

public interface InvoiceDAO {
	Invoice create(Invoice invoice);
	Invoice getById(Long id);
	List<Invoice> getAll();
	Invoice update(Invoice invoice);
	void delete(Long id);
}
