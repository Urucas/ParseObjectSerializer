package com.urucas.parseobjectserializer;

import android.test.AndroidTestCase;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
        assertNotNull(json);
        assertEquals(true, json.has("url"));
    }

    @Test
    public void testPushesString() throws JSONException, ParseException {
        JSONObject json = new JSONObject();
        String key = "key";
        String property = "property";
        Serializer.Push(json, key, property);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(json.getString(key), property);
    }

    @Test
    public void testPushesBoolean() throws JSONException, ParseException {
        JSONObject json = new JSONObject();
        String key = "key";
        boolean property = true;
        Serializer.Push(json, key, property);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(json.getBoolean(key), property);
    }

    @Test
    public void testPushesJSONObject() throws JSONException, ParseException {
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

    @Test
    public void testPushesJSONArray() throws JSONException, ParseException {
        JSONObject json = new JSONObject();
        String key = "da-key";
        JSONArray jsonArray = new JSONArray();
        String property = "property";
        jsonArray.put(property);
        jsonArray.put(4);
        jsonArray.put(new JSONObject());

        Serializer.Push(json, key, jsonArray);
        assertNotNull(json);
        assertEquals(true, json.has(key));
        assertEquals(jsonArray, json.getJSONArray(key));
    }

    @Test
    public void testPushesDate() throws JSONException, ParseException {
        Date date = Calendar.getInstance().getTime();
        JSONObject json = new JSONObject();
        String key = "key";
        Serializer.Push(json, key, date);

        assertNotNull(json);
        assertEquals(true, json.has(key));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        assertEquals(json.getString(key), formatter.format(date));
    }

    @Test
    public void testSerializer() throws JSONException {

        ParseObject object = new ParseObject("Test");
        String objId = "123456";
        object.setObjectId(objId);

        double lat = -32.9479262, lng = -60.6442077;
        ParseGeoPoint point = new ParseGeoPoint(lat, lng);
        String keyLocation = "location";
        object.put(keyLocation, point);

        ParseFile file = new ParseFile(new byte[] { (byte) 0xFF });
        String keyFile = "myFile";
        object.put(keyFile, file);

        Number n = new RandomNumber();
        String keyNumber = "random";
        object.put(keyNumber, n);

        Date now = Calendar.getInstance().getTime();
        String keyDate = "now";
        object.put(keyDate, now);

        String keyBool = "isATest";
        object.put(keyBool, true);

        JSONObject json = new JSONObject();
        String keyJson = "what", valJson = "put a bird on that", keyObject = "obj";
        json.put(keyJson, valJson);
        object.put(keyObject, json);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);
        String keyArray = "arr";
        object.put(keyArray, jsonArray);

        String keySong = "song", song = "pinini reggae";
        object.put(keySong, song);

        JSONObject result = Serializer.Serialize(object);

        assertNotNull(result);
        assertEquals(true, result.has("id"));
        assertEquals(objId, result.getString("id"));
        assertEquals(true, result.has(keyLocation));
        assertNotNull(result.getJSONObject(keyLocation));
        assertEquals(lat, result.getJSONObject(keyLocation).getDouble("lat"));
        assertEquals(lng, result.getJSONObject(keyLocation).getDouble("lng"));
        assertEquals(true, result.has(keyFile));
        assertEquals(true, result.getJSONObject(keyFile).has("url"));
        assertEquals(true, result.has(keyNumber));
        assertEquals(n.doubleValue(), result.getDouble(keyNumber));

        assertEquals(true, result.has(keyDate));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        assertEquals(formatter.format(now), result.getString(keyDate));

        assertEquals(true, result.has(keyBool));
        assertEquals(true, result.getBoolean(keyBool));
        assertEquals(true, result.has(keyObject));
        assertNotNull(result.getJSONObject(keyObject));
        assertEquals(true, result.getJSONObject(keyObject).has(keyJson));
        assertEquals(valJson, result.getJSONObject(keyObject).getString(keyJson));
        assertEquals(true, result.has(keyArray));
        assertNotNull(result.getJSONArray(keyArray));
        assertEquals(true, result.has(keySong));
        assertEquals(song, result.getString(keySong));
    }

    private class RandomNumber extends Number{

        private Random r = new Random();
        private double v = r.nextDouble();

        @Override
        public double doubleValue() {
            return v;
        }

        @Override
        public float floatValue() {
            return (float) v;
        }

        @Override
        public int intValue() {
            return (int) v;
        }

        @Override
        public long longValue() {
            return (long) v;
        }
    }
}
