package com.dual.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Invoice
 *
 */
@Entity

public class ExchangeRate implements Serializable {
	
	private static final long serialVersionUID = -7930233665348223176L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private Date date;
	@NotNull
	@Column(nullable=false, precision=24, scale=12)
	private BigDecimal hufToEurMultiplier;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getHufToEurMultiplier() {
		return hufToEurMultiplier;
	}
	public void setHufToEurMultiplier(BigDecimal hufToEurMultiplier) {
		this.hufToEurMultiplier = hufToEurMultiplier;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeRate [date=");
		builder.append(date);
		builder.append(", hufToEurMultiplier=");
		builder.append(hufToEurMultiplier);
		builder.append("]");
		return builder.toString();
	}
	
	
}
