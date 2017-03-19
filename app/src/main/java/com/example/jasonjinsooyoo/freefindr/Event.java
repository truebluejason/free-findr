package com.example.jasonjinsooyoo.freefindr;

import com.example.jasonjinsooyoo.freefindr.ENUM.Categories;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */

public class Event {

    private String name;
    private int id;
    private double lat;
    private double lon;
    private Categories type;
    private String description;

    public Event(String name) {
        this.name = name;
    }

    // Setters
    public void setID(int id) {
        this.id = id;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public void setType(Categories type) {
        this.type = type;
    }
    public void setDesc(String description) {
        this.description = description;
    }

    // Getters
    public int getID() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}

    // Two events are equal if and only if their id are the same
    @Override
    public boolean equals(Object o) {
        if (o==null || o.getClass()!=this.getClass()) return false;
        Event ye = (Event) o;
        return ye.id == id;
    }
    @Override
    public int hashCode() {
        return id;
    }
}
