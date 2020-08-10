package com.dual.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-08-08T21:29:58.357+0200")
@StaticMetamodel(Invoice.class)
public class Invoice_ {
	public static volatile SingularAttribute<Invoice, Long> id;
	public static volatile SingularAttribute<Invoice, String> customerName;
	public static volatile SingularAttribute<Invoice, Date> issueDate;
	public static volatile SingularAttribute<Invoice, Date> dueDate;
	public static volatile SingularAttribute<Invoice, String> comment;
	public static volatile ListAttribute<Invoice, InvoiceItem> items;
}
