package com.dual.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-08-08T23:09:35.834+0200")
@StaticMetamodel(InvoiceItem.class)
public class InvoiceItem_ {
	public static volatile SingularAttribute<InvoiceItem, Long> id;
	public static volatile SingularAttribute<InvoiceItem, Invoice> invoice;
	public static volatile SingularAttribute<InvoiceItem, String> productName;
	public static volatile SingularAttribute<InvoiceItem, String> unit;
	public static volatile SingularAttribute<InvoiceItem, BigDecimal> unitPrice;
	public static volatile SingularAttribute<InvoiceItem, BigDecimal> quantity;
}
