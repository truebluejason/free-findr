package com.example.jasonjinsooyoo.freefindr;

import java.util.ArrayList;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */
public class EventManager {
    private static EventManager ourInstance = new EventManager();
    private ArrayList eventList;

    public static EventManager getInstance() {
        return ourInstance;
    }

    private EventManager() {
        eventList = new ArrayList<Event>();
    }

    public void addEvent(Event e) {
        eventList.add(e);
    }

    public Event getEvent(int id) {
        Event custom = new Event("filler");
        custom.setID(id);
        if (eventList.contains(custom)) return (Event) eventList.get(eventList.indexOf(custom));
        return null;
    }

    public void removeEvent(int id) {
        Event custom = new Event("filler");
        custom.setID(id);
        if (eventList.contains(custom)) eventList.remove(eventList.indexOf(custom));
    }
}
