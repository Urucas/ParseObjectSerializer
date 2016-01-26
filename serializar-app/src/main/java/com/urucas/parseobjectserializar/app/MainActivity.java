package com.urucas.parseobjectserializar.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.urucas.parseobjectserializer.Serializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by vruno on 1/26/16.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        try {
            String keyJson = "what", valJson = "put a bird on that", keyObject = "obj";
            json.put(keyJson, valJson);
            object.put(keyObject, json);
        }catch (Exception e){};

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);
        String keyArray = "arr";
        object.put(keyArray, jsonArray);

        String keySong = "song", song = "pinini reggae";
        object.put(keySong, song);

        TextView textView = (TextView) findViewById(R.id.serializedText);
        textView.setText(Serializer.Serialize(object).toString());
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
