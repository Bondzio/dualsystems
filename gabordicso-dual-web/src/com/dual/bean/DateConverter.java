package com.dual.bean;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter extends DateTimeConverter {
	public static final String datePattern = "yyyy.MM.dd.";
	public DateConverter() {
		setPattern(datePattern);
		setLocale(new Locale("hu-HU"));
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.length() != getPattern().length()) {
			throw new ConverterException("Invalid format");
		}

		return super.getAsObject(context, component, value);
	}

}
