package com.hack.events.xplorer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    
    @Override
	public String toString() {
		return "Event [eid=" + eid + ", name=" + name + ", description="
				+ description + ", start_time=" + start_time + ", end_time="
				+ end_time + ", location=" + location + ", venueid=" + venueid
				+ ", venue=" + venue + ", attending_count=" + attending_count
				+ ", picUrl=" + picUrl + "]";
	}

	// Looking for OPEN Events
    public String eid;              // EventID
    public String name;             // Event Name
    public String description;      // Description
    public String start_time;       // Start Time
    public String end_time;         // End Time
    public String location;         // Location
    public String venueid;          // street, city, state, zip, country, latitude, and longitude.
    public Venue venue;
    public int attending_count;     // No. of ppl attending the event
    public String picUrl;			// URL of the pic square image
    
    public Event() {
        super();
    }
    
    public Date getDate() {
        
        Date dateObject = null;
        
        String date = start_time;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
            dateObject = sdf.parse(date);
        } 
        catch (ParseException e)
        {
            //e.printStackTrace();
        }
        
        return dateObject;
    }
    
public Date getBeginTime() {
        
        Date dateObject = null;
        
        String date = start_time;
        String pattern = "yyyy-MM-dd'T'HH:mm:ssz";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
            System.out.println("Date " + date);
        	dateObject = sdf.parse(date);
        } 
        catch (ParseException e)
        {
            System.out.println("Error here ");
        }
        
        System.out.println("Here");
        return dateObject;
    }

	public Date getEndTime() {
    
    Date dateObject = null;
    
    String date = end_time;
    String pattern = "yyyy-MM-dd'T'HH:mm:ssz";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try
    {
        dateObject = sdf.parse(date);
    } 
    catch (ParseException e)
    {
        //e.printStackTrace();
    }
    
    return dateObject;
}
}
