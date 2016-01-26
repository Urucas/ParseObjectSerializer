package com.urucas.parseobjectserializer;

import android.test.AndroidTestCase;
import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;

import junit.framework.TestResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vruno on 1/26/16.
 */
public class SerializerTest extends AndroidTestCase {


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testParseDate() throws ParseException {
        Date date = Calendar.getInstance().getTime();
        String strDate = Serializer.ParseDate(date);
        assertNotNull(strDate);
        assertNotSame("", strDate);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        Date reverseDate = formatter.parse(strDate);
        assertEquals(date.getTime(), reverseDate.getTime());
    }

    @Test
    public void testParseGeoPoint() throws JSONException {
        double lat = -32.9479262, lng = -60.6442077;
        ParseGeoPoint point = new ParseGeoPoint(lat, lng);
        JSONObject json = Serializer.Serialize(point);
        assertNotNull(json);
        assertEquals(true, json.has("lat"));
        assertEquals(true, json.has("lng"));
        assertEquals(json.getDouble("lat"), lat);
        assertEquals(json.getDouble("lng"), lng);
    }

    @Test
    public void testParseFile() throws JSONException {
        ParseFile file = new ParseFile(new byte[] { (byte) 0xFF });
        JSONObject json = Serializer.Serialize(file);
        Log.i("json file", json.toString());
        assertNotNull(json);
        assertEquals(true, json.has("url"));
    }

    @Test
    public void testPushesString() throws JSONException {
        JSONObject json = new JSONObject();
        String key = "key";
        String property = "property";
        Serializer.Push(json, key, property);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(json.getString(key), property);
    }

    @Test
    public void testPushesBoolean() throws JSONException {
        JSONObject json = new JSONObject();
        String key = "key";
        boolean property = true;
        Serializer.Push(json, key, property);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(json.getBoolean(key), property);
    }

    @Test
    public void testPushesJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        String key = "key";
        JSONObject json2push = new JSONObject();
        json2push.put("what", "put a bird on that");
        Serializer.Push(json, key, json2push);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(json2push, json.getJSONObject(key));
        assertEquals(json2push.getString("what"), json.getJSONObject(key).getString("what"));
    }
}
