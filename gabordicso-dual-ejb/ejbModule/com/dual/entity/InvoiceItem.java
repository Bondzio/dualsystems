package com.dual.entity;

import com.dual.entity.Invoice;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: InvoiceItem
 *
 */
@Entity

public class InvoiceItem implements Serializable {
	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@ManyToOne(targetEntity = Invoice.class, fetch = FetchType.LAZY)
	private Invoice invoice;
	@NotNull
	@Length(max = 100)
	private String productName;
	@Length(max = 100)
	private String unit;
	@NotNull
	@Column(nullable=false, precision=24, scale=12)
	private BigDecimal unitPrice;
	@NotNull
	@Column(nullable=false, precision=24, scale=12)
	private BigDecimal quantity;
	@Transient
	private BigDecimal totalItemPrice;

	public InvoiceItem() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}   
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}   
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}   
	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}   
	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}   
	public BigDecimal getTotalItemPrice() {
		if (this.quantity == null || this.unitPrice == null) {
			return BigDecimal.ZERO;
		}
		return this.quantity.multiply(this.unitPrice).setScale(2, RoundingMode.HALF_UP);
	}

}
