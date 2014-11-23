package com.oakfusion.security.cookie;

import java.util.Date;

public class ExpirationDate {

	private final Date date;

	public ExpirationDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
