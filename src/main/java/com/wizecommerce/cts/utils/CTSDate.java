package com.wizecommerce.cts.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is a utility class to handle date through out CTS
 * @author panand
 */
public class CTSDate {
	
	public String epochToString(int epochTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		Date date_ = new Date( epochTime * 1000L );
		return sdf.format(date_);
	}
}