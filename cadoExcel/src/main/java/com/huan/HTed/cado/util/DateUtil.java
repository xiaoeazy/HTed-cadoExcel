package com.huan.HTed.cado.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
		public static  Date getLocalDate(){
			 SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 String d = format.format(new Date());  
			 Date date;
			try {
				date = format.parse(d);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			} 
			 return date;
		}
}
