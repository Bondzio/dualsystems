package com.dual.entity;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: Invoice
 *
 */
@Entity

public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Length(max = 100)
	private String customerName;
	@NotNull
	private Date issueDate;
	@NotNull
	private Date dueDate;
	@Length(max = 200)
	private String comment;
	@Transient
	private BigDecimal total;
	@OneToMany(targetEntity = InvoiceItem.class, fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
	private List<InvoiceItem> items;

	public Invoice() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}   
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}   
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}   
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}   
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		if (items != null) {
			for (InvoiceItem item : items) {
				total = total.add(item.getTotalItemPrice());
			}
		}
		return total;
	}

	public List<InvoiceItem> getItems() {
		return this.items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}
   
}
