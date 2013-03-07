package com.hack.events.xplorer;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonParser {
    
    
    public JsonParser() {
        super();
    }
    
    public static Map<String, String> fromEventAttrsJson(String imgJsonData) {
    	Map<String, String> eventAttrsMap = new HashMap<String, String>();

    	JSONObject obj = (JSONObject)JSONValue.parse(imgJsonData);
    	JSONArray obj2 = (JSONArray)obj.get("data");
    	
    	JSONObject eventAttrs = (JSONObject)obj2.get(0);
    	String imgUrl = null;
    	if(eventAttrs != null) {
    		imgUrl = (String)eventAttrs.get("pic_small"); 
    	}

    	String attendingCount = eventAttrs.get("attending_count").toString();
    	eventAttrsMap.put("imageUrl", imgUrl);
    	eventAttrsMap.put("attendingCount", attendingCount);

    	return eventAttrsMap;
    }

    public static Venue fromVenueJson(String s) {
        Venue venue = new Venue();
        JSONObject obj2 = (JSONObject)JSONValue.parse(s);
        JSONObject location = (JSONObject)obj2.get("location");
        venue.latitude = (String) location.get("latitude").toString();
        venue.longitude = (String) location.get("longitude").toString();
        venue.city = (String)location.get("city");
        venue.state = (String)location.get("state");
        venue.country = (String)location.get("country");
        
        return venue;
    }
    
    public static EventPage fromJson(String s) {
        
        EventPage eventPage = new EventPage();
        
        JSONObject obj2 = (JSONObject)JSONValue.parse(s);
        
        JSONArray array = (JSONArray)obj2.get("data");
        
        for(int i = 0; i < array.size(); i++) {
            Event event = new Event();
            JSONObject obj = (JSONObject)array.get(i);
            event.eid = (String)obj.get("id");
            event.location = (String)obj.get("location");
            JSONObject temp = (JSONObject)obj.get("venue");
            if(temp != null)
                event.venueid = (String)temp.get("id");
            event.name = (String)obj.get("name");
            event.start_time = (String)obj.get("start_time");
            event.end_time = (String)obj.get("end_time");
            //event.getDate();
            eventPage.add(event);
        }
        
        JSONObject obj3 = (JSONObject)obj2.get("paging");
        eventPage.previousPage = (String)(obj3.get("previous"));
        eventPage.nextPage = (String)(obj3.get("next"));
        
        return eventPage;
    }
}
