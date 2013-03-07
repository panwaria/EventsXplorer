package com.hack.events.xplorer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Test class for getting REST output from facebook APIs.
 */
public class Labs {
	public static void main(String[] args) {

		FBRestUtil util = new FBRestUtil();
		String location = "New York";
		
        String date = "2012-12-31";
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dateObject = null;
        try
        {
            dateObject = sdf.parse(date);
            System.out.println(dateObject);
        } 
        catch (ParseException e)
        {
            //e.printStackTrace();
        }

////        List<Event> events = util.getEventData(location, dateObject);
//		System.out.println("Found " + events.size() + " events");
//		for(Event e : events) {
//			System.out.println(e.toString());
//		}

		
	}
 
}