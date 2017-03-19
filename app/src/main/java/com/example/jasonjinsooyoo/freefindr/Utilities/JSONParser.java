package com.example.jasonjinsooyoo.freefindr.Utilities;

import com.example.jasonjinsooyoo.freefindr.ENUM.Categories;
import com.example.jasonjinsooyoo.freefindr.Event;
import com.example.jasonjinsooyoo.freefindr.EventManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */

public class JSONParser {

    public static void parseJSONEventData(String string) throws JSONException {
        JSONArray myArray = new JSONArray(string);
        for (int i=0; i<myArray.length(); i++) {
            JSONObject curObj = myArray.getJSONObject(i);
            storeToManager(curObj);
        }
    }

    // parses and stores data to EventManager
    public static void storeToManager(JSONObject curObj) throws JSONException {
        int id = curObj.getInt("id");
        String name = curObj.getString("name");
        double lat = curObj.getDouble("latitude");
        double lon = curObj.getDouble("longitude");
        String type = curObj.getString("type");
        Categories cat;
        if (type.equals("ART")) {
            cat = Categories.ART;
        } else if (type.equals("BUSINESS")) {
            cat = Categories.BUSINESS;
        } else if (type.equals("FOOD")) {
            cat = Categories.FOOD;
        } else if (type.equals("GAME")) {
            cat = Categories.GAME;
        } else if (type.equals("HEALTH")) {
            cat = Categories.HEALTH;
        } else if (type.equals("OTHER")) {
            cat = Categories.OTHER;
        } else if (type.equals("SOCIAL")) {
            cat = Categories.SOCIAL;
        } else if (type.equals("SPORTS")) {
            cat = Categories.SPORTS;
        } else if (type.equals("TECH")) {
            cat = Categories.TECH;
        } else {
            throw new RuntimeException("Nonexistent category");
        }
        String description = curObj.getString("description");

        // Creates a new event
        Event myEvent = new Event(name);
        myEvent.setID(id);
        myEvent.setLat(lat);
        myEvent.setLon(lon);
        myEvent.setType(cat);
        myEvent.setDesc(description);

        EventManager.getInstance().addEvent(myEvent);
    }
}
