package com.example.jasonjinsooyoo.freefindr;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jasonjinsooyoo.freefindr.ENUM.Categories;

import java.io.Serializable;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */

public class Event implements Parcelable {

    private String name;
    private int id;
    private double lat;
    private double lon;
    private Categories type;
    private String description;

    public Event(String name) {
        this.name = name;
    }

    private Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        type = Categories.valueOf(in.readString());
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(type.toString());
        dest.writeString(description);
    }

    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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
    public String getType() {
        if (type==Categories.ART) return "ART";
        if (type==Categories.BUSINESS) return "BUSINESS";
        if (type==Categories.FOOD) return "FOOD";
        if (type==Categories.GAME) return "GAME";
        if (type==Categories.HEALTH) return "HEALTH";
        if (type==Categories.OTHER) return "OTHER";
        if (type==Categories.SOCIAL) return "SOCIAL";
        if (type==Categories.SPORTS) return "SPORTS";
        if (type==Categories.TECH) return "TECH";
        return null;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }


}
