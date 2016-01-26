package com.urucas.parseobjectserializer;

/**
 * Created by vruno on 1/26/16.
 */
import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by vruno on 1/25/16.
 */
public abstract class Serializer {

    public static JSONObject Serialize(ParseObject object) {

        JSONObject json = new JSONObject();
        if(object == null) return json;

        try {
            String property = object.getObjectId();
            json.put("id", property);

            Date date = object.getCreatedAt();
            json.put("createdAt", Serializer.ParseDate((Date)date));

            date = object.getUpdatedAt();
            json.put("updatedAt", Serializer.ParseDate((Date)date));

        }catch(Exception e){}

        for(String key: object.keySet()) {
            try { Push(json, key, object.get(key)); }catch(Exception e){ continue; }
        }
        return json;
    }

    public static JSONObject Push(JSONObject json, String key, Object property) {
        try {
            if (property == null)
                return json.put(key, "");

            if (property instanceof String || property instanceof Boolean
                    || property instanceof JSONArray || property instanceof JSONObject)
                return json.put(key, property);

            if (property instanceof Number)
                return json.put(key, ((Number) property).doubleValue());

            if (property instanceof ParseFile)
                return json.put(key, Serializer.Serialize((ParseFile) property));

            if (property instanceof ParseGeoPoint)
                return json.put(key, Serializer.Serialize((ParseGeoPoint) property));

            if (property instanceof Date)
                return json.put(key, Serializer.ParseDate((Date) property));

        }catch(Exception e){}
        return json;
    }

    public static String ParseDate(Date date) throws ParseException {
        String dateAsStr;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        dateAsStr = formatter.format(date);
        return dateAsStr;
    }

    public static JSONObject Serialize(ParseFile file) throws JSONException{
        JSONObject json = new JSONObject();
        json.put("url", "");
        if(file != null && file.getUrl() != null) json.put("url", file.getUrl());
        return json;
    }

    public static JSONObject Serialize(ParseGeoPoint point) throws JSONException {
        JSONObject json = new JSONObject();
        if(point != null) {
            json.put("lat", point.getLatitude());
            json.put("lng", point.getLongitude());
        }else{
            json.put("lat", 0);
            json.put("lng", 0);
        }
        return json;
    }
}

