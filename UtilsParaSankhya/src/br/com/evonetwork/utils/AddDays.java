package br.com.evonetwork.utils;

import java.sql.Timestamp;

public class AddDays {
	public static Timestamp addDays(int days, Timestamp t1) throws Exception{
	    if(days < 0){
	        throw new Exception("Day in wrong format.");
	    }
	    Long miliseconds = dayToMiliseconds(days);
	    return new Timestamp(t1.getTime() + miliseconds);
	}
	
	public static Long dayToMiliseconds(int days){
	    Long result = Long.valueOf(days * 24 * 60 * 60 * 1000);
	    return result;
	}
}
