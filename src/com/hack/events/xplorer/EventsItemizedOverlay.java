package com.hack.events.xplorer;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayView;

public class EventsItemizedOverlay<Item extends OverlayItem> extends BalloonItemizedOverlay<EventOverlayItem> //ItemizedOverlay
{
	private ArrayList<EventOverlayItem> mOverlays = new ArrayList<EventOverlayItem>();
	private Context mContext;
	
/*	public EventsItemizedOverlay(Drawable defaultMarker, MapView mapView) 
	{
		super(defaultMarker, mapView);
		// TODO Auto-generated constructor stub
	}*/
	
	public EventsItemizedOverlay(Drawable defaultMarker, MapView mapView)
	{
		super(boundCenter(defaultMarker), mapView);
		mContext = mapView.getContext();
	}


	/*
	public EventsItemizedOverlay(Drawable defaultMarker) 
	{
		  super(boundCenterBottom(defaultMarker));
	}
	
	public EventsItemizedOverlay(Drawable defaultMarker, Context context) 
	{
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
	}*/
	
	public void addOverlay(EventOverlayItem overlay) 
	{
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected EventOverlayItem createItem(int i) 
	{
	  return mOverlays.get(i);
	}

	@Override
	public int size() 
	{
	  return mOverlays.size();
	}
	
	/*
	protected boolean onTap(int index)
	{
		  OverlayItem item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
		  return true;
		}*/
	
	@Override
	protected boolean onBalloonTap(int index, EventOverlayItem item) 
	{	
		Intent calIntent = new Intent(Intent.ACTION_EDIT);
//		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra("title", item.event.name);
		
		if(item.event.getBeginTime() == null || item.event.getEndTime() == null)
		{
			calIntent.putExtra("allDay", true);
		    calIntent.putExtra("beginTime", item.event.getDate().getTime());
		}
		else
		{
		    calIntent.putExtra("beginTime", item.event.getBeginTime().getTime());
		    calIntent.putExtra("endTime", item.event.getEndTime().getTime());			
		}
		calIntent.putExtra("eventLocation", item.event.location);
		
//		calIntent.putExtra(CalendarContract.Events.TITLE, item.event.name);
//		calIntent.putExtra(Events.EVENT_LOCATION, item.event.location);
//		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, item.event.start_time);
		mContext.startActivity(calIntent);
		
		return true;
	}
	
	@Override
	protected BalloonOverlayView<EventOverlayItem> createBalloonOverlayView()
	{
		Toast tm = Toast.makeText(mContext, "Tap on the balloon to add a calendar event", 600);
		tm.show();
		// use our custom balloon view with our custom overlay item type:
		return new EventBalloonOverlayView<EventOverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}

	
}
