package com.hack.events.xplorer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class EventsMapActivity extends MapActivity
{
//	EventsItemizedOverlay itemizedOverlay;
	EventsItemizedOverlay<EventOverlayItem> itemizedOverlay;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.eventsmap);

	    mapView = (MapView) findViewById(R.id.mapview_eventsmap);
	    mapView.setBuiltInZoomControls(true);

	    mapOverlays = mapView.getOverlays();

	    drawable = getResources().getDrawable(R.drawable.androidmarker);   
	    
//	    GeoPoint point = new GeoPoint(19240000,-99120000);
//	    OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
//	    itemizedoverlay.addOverlay(overlayitem);
//
//	    
//	    GeoPoint point2 = new GeoPoint(35410000, 139460000);
//	    OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
//	    itemizedoverlay.addOverlay(overlayitem2);

	    
	    // TESTING-------------------------------------
		FBRestUtil util = new FBRestUtil();
		Intent callerIntent = getIntent();
		Date dummyDate = new Date();
		String location = callerIntent.getStringExtra(HomeActivity.LOCATION_STRING);
		String state = callerIntent.getStringExtra(HomeActivity.STATE_STRING);
		
		String stateCode = CityMap.getAbbrevForStateName(state.trim());
		
		int day = callerIntent.getIntExtra(HomeActivity.DAY_STRING, dummyDate.getDay());
		int month = callerIntent.getIntExtra(HomeActivity.MONTH_STRING, dummyDate.getMonth());
		int year = callerIntent.getIntExtra(HomeActivity.YEAR_STRING, dummyDate.getYear());
		
		Date dateObject = new Date(year-1900, month, day);
		
//        String date = callerIntent.getStringExtra(HomeActivity.DATE_STRING);
//        
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        Date dateObject = null;
//        try
//        {
//            dateObject = sdf.parse(date);
//            System.out.println(dateObject);
//        } 
//        catch (ParseException e)
//        {
//            //e.printStackTrace();
//        }

        List<Event> events;
		try 
		{
			events = util.getEventData(location, stateCode, dateObject);
			
	       if(events.size() == 0)
	        {

	  		 	Intent intent = new Intent();
			    setResult(HomeActivity.RETURN_CODE_NO_EVENT, intent);
	  		    finish();
			}
	        else
	        {
	        	int lat = 0, lon = 0;
//		        	EventsItemizedOverlay itemizedoverlay = new EventsItemizedOverlay(drawable, mapView);
	        	
	    		System.out.println("Found " + events.size() + " events");
	    		for(Event e : events)
	    		{
	    			double lat1 = 1000000.0*Double.parseDouble(e.venue.latitude);
	    			double lon1 = 1000000.0*Double.parseDouble(e.venue.longitude);
	    			
	    			lat = (int)lat1;
	    			lon = (int)lon1;
	    			
	    		    itemizedOverlay = new EventsItemizedOverlay(drawable, mapView);

	    			// Creating the OverlayItem
	    		    GeoPoint testPoint = new GeoPoint(lat, lon);
	    			Date date = e.getBeginTime();
	    			if(date == null)
	    				date = e.getDate();
	    		    EventOverlayItem overlayitem = new EventOverlayItem(testPoint, e, date);
	    		    
	    		    // Add this overlay item
	    		    itemizedOverlay.addOverlay(overlayitem);
	    		    
		    		// Finally adding the overlay to the Main list
		    		mapOverlays.add(itemizedOverlay);
	    		}

	    		if (savedInstanceState == null) {

	    			// Setting Map Controller
//			    	    MapController mc = mapView.getController();
//			    	    mc.zoomToSpan(itemizedOverlay.getLatSpanE6(), itemizedOverlay.getLonSpanE6());
//			    	    mc.setCenter(new GeoPoint(lat, lon));

	    			final MapController mc = mapView.getController();
	    			mc.animateTo(new GeoPoint(lat, lon));
	    			mc.setZoom(16);
	    			
	    		} 
	    		else 
	    		{
	    			
	    			// example restoring focused state of overlays
	    			int focused;
	    			focused = savedInstanceState.getInt("focused_1", -1);
	    			if (focused >= 0) 
	    			{
	    				itemizedOverlay.setFocus(itemizedOverlay.getItem(focused));
	    			}

	    		}
	        }
		}
		catch (Exception e1) 
		{
 		 	Intent intent = new Intent();
		    setResult(HomeActivity.RETURN_CODE_SERVER_ERROR, intent);
  		    finish();
		}
	}

	@Override
	protected boolean isRouteDisplayed() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{	
		// example saving focused state of overlays
		if (itemizedOverlay.getFocus() != null) outState.putInt("focused_1", itemizedOverlay.getLastFocusedIndex());
		super.onSaveInstanceState(outState);
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(0, 0, 1, "Remove Overlay");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == 0)
		{
			
			// example hiding balloon before removing overlay
			if (itemizedOverlay.getFocus() != null)
			{
				itemizedOverlay.hideBalloon();
			}
			mapOverlays.remove(itemizedOverlay);
			mapView.invalidate();
			
		}
		return true;
	}
	
}
