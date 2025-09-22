package com.example.youbike;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import android.content.Context;
import java.util.ArrayList;

public class DockChecker {

    private Context context;

    public DockChecker(Context context) {
        this.context = context;
    }

    public ArrayList<String> checkDocksForAnomalies() {
        ArrayList<String> anomalies = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("Docks.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray docks = new JSONArray(json);

            for (int i = 0; i < docks.length(); i++) {
                JSONObject dock = docks.getJSONObject(i);
                String stationUID = dock.getString("StationUID");
                //String DockUID = dock.getString("DockUID");

                String bike = dock.getString("Bike");

                if (stationUID.startsWith("TPE") && !bike.startsWith("A") && !bike.isEmpty()) {
                    anomalies.add("Station UID: " + stationUID + ", Bike ID: " + bike);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anomalies;
    }
}
