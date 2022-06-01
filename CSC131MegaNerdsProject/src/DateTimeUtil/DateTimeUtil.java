package DateTimeUtil;

import java.time.LocalDateTime;

/*
 * Class: DateTimeUtil
 * Provides helper methods related to LocalDateTime
 */

public class DateTimeUtil {
	
	//accepts two Strings and returns a LocalDateTime
	public static LocalDateTime dateTimeStringParse(String date, String time) {
		String year = "";
		String month = "";
		String day = "";
			
		String hour = "";
		String minute = "";
		String second = "";
			
		String result = "";
		try {
			year = date.substring(0,4); //year
			month = date.substring(5,7); //month
			day = date.substring(8,date.length()); //day
			
			hour = time.substring(0,2);//hour
			minute = time.substring(3,5);//minute
			second = time.substring(6,8);//seconds

			result = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
		
			return LocalDateTime.parse(result);
		} catch (Exception e) {
			return null;
		}
	}
}
