
public class NowTime {

	public static String getTime() {
		//Read the time zone offset
//		System.out.print("Enter the time zone offset to GMT: ");
//		Scanner input = new Scanner(System.in);
		long timeZoneOffset = 8;
		
		//Obtain the total milliseconds since midnight, Jan 1, 1970
		long totalMilliseconds = System.currentTimeMillis();
		
		//Obtain the total seconds since midnight, Jan 1, 1970
		long totalSeconds = totalMilliseconds / 1000;
		
		//Compute the current second in the minute in the hour
		long currentSecond = totalSeconds % 60;
		
		//Obtain the total minutes
		long totalMinutes = totalSeconds / 60;
		
		//Compute the current minute in the hour
		long currentMinute = totalMinutes % 60;
		
		//Obtain the total hours
		long totalHours = totalMinutes / 60;
		
		//Compute the current hour
		long currentHour = totalHours % 24;
		currentHour += timeZoneOffset;
		if(currentHour >= 24)
			currentHour -= 24;
		else if(currentHour < 0)
			currentHour = 24 - currentHour;
		
		//Display results
		String time = new String(currentHour + ":" + currentMinute + ":" + currentSecond);
		return time;
	}

}
