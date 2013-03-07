package com.hack.events.xplorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class provides helper methods to access facebook graph API by making RESTful calls.
 */
public class FBRestUtil {
	
	// This token changes every 2 hours.
	public static final String FB_TOKEN = "AAACEdEose0cBAC10S4FLB2DyalH2IFL50a4l86nFvXLAcGWeEr6guRKohTO69kRkZApj4GXEP49ej1Vldhr7uGNgqqey6xWU67qxPcgZDZD";
	public static final int NUM_ENTRIES = 1000;
	
	// Generates the base URL for making RESTful calls to Facebook Graph API
	public String getEventLocURL(String location)
	{
		StringBuilder url = new StringBuilder();
		url.append("https://graph.facebook.com/search?fields=location,name,venue,end_time&q=");
		try {
			url.append(URLEncoder.encode(location,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.append("&type=event");
		url.append("&limit=" + NUM_ENTRIES);
		url.append("&access_token=" + FB_TOKEN);
		
		return url.toString();
	}

	public String getVenueURL(String venueId)
	{
		return "https://graph.facebook.com/" + venueId;
	}

	public String getEventAttrsURL(String eid)
	{
		StringBuilder eventAttrsUrl = new StringBuilder();
		
		eventAttrsUrl.append("https://graph.facebook.com/fql?q=SELECT%20pic_small%20,attending_count%20FROM%20event%20WHERE%20");
		eventAttrsUrl.append("eid=" + eid);
		eventAttrsUrl.append("&access_token=" + FB_TOKEN);

		return eventAttrsUrl.toString();
	}

	/**
	 * Returns Event data in JSON format for the location and date specified
	 */
	public List<Event> getEventData(String city, String state, Date date) throws Exception {
		List<Event> events = new ArrayList<Event>();
		
		String baseUrl = getEventLocURL(city);
		System.out.println("Search URL : " + baseUrl);
		String jsonData;
		try {
			jsonData = getJSONDataForURL(baseUrl);
		} catch (Exception e1) {
			throw e1;

		}
		
		// Extract raw events from the base url RESTful call 
		EventPage baseEventPage = JsonParser.fromJson(jsonData);
		List<Event> rawEvents = baseEventPage.events;
		if (rawEvents.isEmpty()) {
			return events;
		}

		// Apply filter on events for date input parameter
		List<Event> dateFilteredEvents = getDateFilteredEvents(rawEvents, date);
		if(dateFilteredEvents.isEmpty()) {
			return events;
		}

		String venueUrl = null;
		for(Event e : dateFilteredEvents) {
			venueUrl = getVenueURL(e.venueid);
			if(e.venueid == null) {
				continue;
			}
			
			try {
				jsonData = getJSONDataForURL(venueUrl);				
			}
			catch (Exception ex) {
				System.out.println("Failed to parse venue url " + venueUrl);
			}

			if(jsonData != null) {
				e.venue = JsonParser.fromVenueJson(jsonData);
			}
		}

		events.addAll(dateFilteredEvents);

		// Apply filter for location input parameter
		List<Event> finalEvents = getLocationFilteredEvents(events, city, state);

		// Populate event with other relevant attributes
		if(!finalEvents.isEmpty()) {
			for(Event e : finalEvents) {
				jsonData = getJSONDataForURL(getEventAttrsURL(e.eid));
				Map<String, String> eventAttrsMap = JsonParser.fromEventAttrsJson(jsonData);
				
				e.picUrl = eventAttrsMap.get("imageUrl");
				if(eventAttrsMap.containsKey("attendingCount")) {
					e.attending_count = Integer.parseInt(eventAttrsMap.get("attendingCount"));	
				}
				
			}
		}
		
		return finalEvents;
	}

	public static List<Event> getDateFilteredEvents(List<Event> events, Date date)
	{
		if(date == null) {
			return events;
		}

		List<Event> newEvents = new ArrayList<Event>();
		for(Event e : events) {
			if(e.getDate() != null && (e.getDate().equals(date))) {
				newEvents.add(e);
			}
		}
		
		System.out.println("Found " + newEvents.size() + " events after date filtering");
		return newEvents;
	}

	public static List<Event> getLocationFilteredEvents(List<Event> events, String city, String state)
	{
		List<Event> finalEvents = new ArrayList<Event>();
		
		boolean isValidEvent = false;
		for(Event e : events) {
			Venue v = e.venue;
			isValidEvent = false;
			//Venue filter
			if(v != null) {
				if(v.city != null && (v.city.toLowerCase().contains(city.toLowerCase()))) {
					isValidEvent = true;
				}
				if(v.state != null && state != null) {
					if(!(v.state.toLowerCase().equals(state.toLowerCase()))) {
						isValidEvent = false;
					}
				}
			}
			
			if(isValidEvent) {
				finalEvents.add(e);
			}
		}
		
		System.out.println("Found " + finalEvents.size() + " events after location filtering");
		return finalEvents;
	}

	/**
	 * Returns JSON data for a URL
	 */
	private String getJSONDataForURL(String url) {
		StringBuilder jsonDataForURL = new StringBuilder();
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			while ((output = br.readLine()) != null) {
				jsonDataForURL.append(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return jsonDataForURL.toString();
	}
}
