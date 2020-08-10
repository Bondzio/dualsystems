package com.dual.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-08-10T11:13:27.129+0200")
@StaticMetamodel(ExchangeRate.class)
public class ExchangeRate_ {
	public static volatile SingularAttribute<ExchangeRate, Long> id;
	public static volatile SingularAttribute<ExchangeRate, Date> date;
	public static volatile SingularAttribute<ExchangeRate, BigDecimal> hufToEurMultiplier;
}
