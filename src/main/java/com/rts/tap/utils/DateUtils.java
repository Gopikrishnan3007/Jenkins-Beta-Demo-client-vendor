package com.rts.tap.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	public static Date getCurrentDate() {
		return new Date();
	}

	public String getCurrentDateFormatted() {
		return dateFormat.format(getCurrentDate());
	}

}
