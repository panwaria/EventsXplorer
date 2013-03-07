package com.hack.events.xplorer;

import java.util.ArrayList;

public class EventPage {
    public EventPage() {
        super();
        events = new ArrayList<Event>();
    }
    
    public ArrayList<Event> events;
    public String previousPage;
    public String nextPage;
    
    public void add(Event event) {
        events.add(event);
    }
}
