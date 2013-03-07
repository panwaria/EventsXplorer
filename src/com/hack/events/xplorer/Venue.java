package com.hack.events.xplorer;

public class Venue {
    
    public String city;
    public String state;
    public String country;
    public String latitude;
    public String longitude;
    
    @Override
	public String toString() {
		return "Venue [city=" + city + ", state=" + state + ", country="
				+ country + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

	public Venue() {
        super();
    }
}
