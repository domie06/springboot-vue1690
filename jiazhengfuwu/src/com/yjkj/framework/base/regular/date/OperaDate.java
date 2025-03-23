package com.yjkj.framework.base.regular.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperaDate {

	private OperaDate() {};
	
	//增加时间
			public static String addDate(int num,String today) {
				Calendar c = Calendar.getInstance();  
				Date date = null;
				try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(today);
				}catch (ParseException e) {
					throw new RuntimeException("增加日期失败,原因为:"+e.getMessage());
				}
				c.setTime(date);  
		        int day = c.get(Calendar.DATE);  
		        c.set(Calendar.DATE, day + num);  
		        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
		        return dayAfter;  // 返回当前时间，格式，2017-03-29 10:05:37 （年、月、日、时、分、秒；24小时制）
			}
		
		//增加时间
		public static String addDateTime(int num,String today) {
			Calendar c = Calendar.getInstance();  
			Date date = null;
			try {
			date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(today);
			}catch (ParseException e) {
				throw new RuntimeException("增加日期失败,原因为");
			}
			c.setTime(date);  
	        int day = c.get(Calendar.DATE);  
	        c.set(Calendar.DATE, day + num);  
	        String dayAfter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTime());  
	        return dayAfter;  // 返回当前时间，格式，2017-03-29 10:05:37 （年、月、日、时、分、秒；24小时制）
		}
}
