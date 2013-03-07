package com.hack.events.xplorer;

import java.util.HashMap;
import java.util.Map;


public class CityMap {

	public static Map<String, String> mapStateToAbbrev = new HashMap<String, String>();
	
	static {
		mapStateToAbbrev.put("Alabama","AL");
		mapStateToAbbrev.put("Alaska","AK");
		mapStateToAbbrev.put("Arizona","AZ");
		mapStateToAbbrev.put("Arkansas","AR");
		mapStateToAbbrev.put("California","CA");
		mapStateToAbbrev.put("Colorado","CO");
		mapStateToAbbrev.put("Connecticut","CT");
		mapStateToAbbrev.put("Delaware","DE");
		mapStateToAbbrev.put("Florida","FL");
		mapStateToAbbrev.put("Georgia","GA");
		mapStateToAbbrev.put("Hawaii","HI");
		mapStateToAbbrev.put("Idaho","ID");
		mapStateToAbbrev.put("Illinois","IL");
		mapStateToAbbrev.put("Indiana","IN");
		mapStateToAbbrev.put("Iowa","IA");
		mapStateToAbbrev.put("Kansas","KS");
		mapStateToAbbrev.put("Kentucky","KY");
		mapStateToAbbrev.put("Louisiana","LA");
		mapStateToAbbrev.put("Maine","ME");
		mapStateToAbbrev.put("Maryland","MD");
		mapStateToAbbrev.put("Massachusetts","MA");
		mapStateToAbbrev.put("Michigan","MI");
		mapStateToAbbrev.put("Minnesota","MN");
		mapStateToAbbrev.put("Mississippi","MS");
		mapStateToAbbrev.put("Missouri","MO");
		mapStateToAbbrev.put("Montana","MT");
		mapStateToAbbrev.put("Nebraska","NE");
		mapStateToAbbrev.put("Nevada","NV");
		mapStateToAbbrev.put("New Hampshire","NH");
		mapStateToAbbrev.put("New Jersey","NJ");
		mapStateToAbbrev.put("New Mexico","NM");
		mapStateToAbbrev.put("New York","NY");
		mapStateToAbbrev.put("North Carolina","NC");
		mapStateToAbbrev.put("North Dakota","ND");
		mapStateToAbbrev.put("Ohio","OH");
		mapStateToAbbrev.put("Oklahoma","OK");
		mapStateToAbbrev.put("Oregon","OR");
		mapStateToAbbrev.put("Pennsylvania","PA");
		mapStateToAbbrev.put("Rhode Island","RI");
		mapStateToAbbrev.put("South Carolina","SC");
		mapStateToAbbrev.put("South Dakota","SD");
		mapStateToAbbrev.put("Tennessee","TN");
		mapStateToAbbrev.put("Texas","TX");
		mapStateToAbbrev.put("Utah","UT");
		mapStateToAbbrev.put("Vermont","VT");
		mapStateToAbbrev.put("Virginia","VA");
		mapStateToAbbrev.put("Washington","WA");
		mapStateToAbbrev.put("West Virginia","WV");
		mapStateToAbbrev.put("Wisconsin","WI");
		mapStateToAbbrev.put("Wyoming","WY");
	};
	
	public static String getAbbrevForStateName(String state)
	{
		if(state == null || state.length() < 2) {
			return "";
		}

		if(state.length() == 2) {
			return state;
		}
		
		String toReturn = mapStateToAbbrev.get(state);
		if(toReturn == null)
			return "";
		return toReturn;
		
	}
}
