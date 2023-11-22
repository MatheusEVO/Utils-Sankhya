package br.com.evonetwork.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToTimestamp {
	public static Timestamp stringToTimestamp(String data) throws Exception {
		Timestamp timestamp = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(data);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) {
		    e.printStackTrace();
		    throw new Exception(e.getMessage());
		}
		return timestamp;
	}
}
