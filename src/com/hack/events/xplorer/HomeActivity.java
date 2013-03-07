package com.hack.events.xplorer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.maps.MapActivity;

public class HomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
//        // Set current city
//        Geocoder gcd = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses;
//		try 
//		{
//			double lat = 4000000.0, lon = -73000000.0;
//			addresses = gcd.getFromLocation(40.0, -73.0, 1);//lat, lon, 1);
//			
//			if (addresses.size() > 0) 
//		        	((EditText)findViewById(R.id.edittext_location)).setText(addresses.get(0).getLocality());
//		} 
//		catch (IOException e) 
//		{
//			
//		}
//            System.out.println(addresses.get(0).getLocality());
		
		// Get the localized location
		
		LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location !=null)
		{
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
				
				EditText textBox = (EditText)findViewById(R.id.edittext_location);
				textBox.setText(addresses.get(0).getLocality());
				
				String code = CityMap.getAbbrevForStateName(addresses.get(0).getAdminArea().trim());
				
				EditText stateText = (EditText)findViewById(R.id.edittext_state);
				stateText.setText(code);
				
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		
		LocationListener locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			
			@Override
			public void onLocationChanged(Location location) 
			{
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
				try {
					List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
					
					EditText textBox = (EditText)findViewById(R.id.edittext_location);
					textBox.setText(addresses.get(0).getLocality());
					
					String code = CityMap.getAbbrevForStateName(addresses.get(0).getAdminArea().trim());
					
					EditText stateText = (EditText)findViewById(R.id.edittext_state);
					stateText.setText(code);
					
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				

				
			}
		};
		
		//manager.requestLocationUpdates(manager.GPS_PROVIDER, l, f, locationListener);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		

        
        final Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	if( ((EditText)findViewById(R.id.edittext_state)).getText().toString().equals("") && 
            	   ((EditText)findViewById(R.id.edittext_location)).getText().toString().equals(""))
            	   {
        			 Toast tm = Toast.makeText(HomeActivity.this, "Please add your location in the fields above.", 600);
        			 tm.show();
        			 return;
            	   }
            	   
            	// Hide Keyboard
            	
            	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        		if(imm.isActive())
        			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		
        		// Get the date from Date Picker
        		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
 //       		Date date= (Date) new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
 //      		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 //       		String dateString = sdf.format(date);
        		
                // Perform action on click
              	Intent intent = new Intent(HomeActivity.this, EventsMapActivity.class);
              	intent.putExtra(HomeActivity.LOCATION_STRING, ((EditText)findViewById(R.id.edittext_location)).getText().toString());
              	intent.putExtra(HomeActivity.STATE_STRING, ((EditText)findViewById(R.id.edittext_state)).getText().toString());
              	
              	intent.putExtra(HomeActivity.DAY_STRING, datePicker.getDayOfMonth());
              	intent.putExtra(HomeActivity.MONTH_STRING, datePicker.getMonth());
              	intent.putExtra(HomeActivity.YEAR_STRING, datePicker.getYear());//dateString);
              	startActivityForResult(intent, RETURN_CODE);          	
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		switch(requestCode)
		{
		case RETURN_CODE: 
			{
				switch(resultCode)
				{
				case RETURN_CODE_NO_EVENT :
		        	dialog.setTitle("No events found!");
		  		    break;
		  		    
				case RETURN_CODE_SERVER_ERROR :
					dialog.setTitle("Internal Server Error Occured");
					break;
				}
			}
			break;
		}
		
		    dialog.show();
    }

    private static final int RETURN_CODE = 1;
    public static final int RETURN_CODE_NO_EVENT = 1;
    public static final int RETURN_CODE_SERVER_ERROR = 2;
    public static final String LOCATION_STRING = "com.hack.events.xplorer.loc_string";
    public static final String STATE_STRING = "com.hack.events.xplorer.state_string";
    public static final String DAY_STRING = "com.hack.events.xplorer.day_string";
    public static final String MONTH_STRING = "com.hack.events.xplorer.month_string";
    public static final String YEAR_STRING = "com.hack.events.xplorer.year_string";

    /*
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	*/
}
