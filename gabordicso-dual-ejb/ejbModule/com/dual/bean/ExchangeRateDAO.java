package com.dual.bean;

import java.util.Date;

import com.dual.entity.ExchangeRate;

public interface ExchangeRateDAO {
	ExchangeRate getById(Long id);
	ExchangeRate getByDate(Date date) throws MNBFaultException;
	void delete(Long id);
}
