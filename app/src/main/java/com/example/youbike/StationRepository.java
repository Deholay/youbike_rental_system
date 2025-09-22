package com.example.youbike;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StationRepository {

    private static final String TAG = "StationRepository";
    private final Context context;
    private final DatabaseHelperMap dbHelperMap;

    public StationRepository(Context context) {
        this.context = context.getApplicationContext();
        this.dbHelperMap = new DatabaseHelperMap(this.context);
    }

    public void resetDatabase() {
        dbHelperMap.resetDatabase();
    }

    public void importFromAssets() {
        SQLiteDatabase database = dbHelperMap.getWritableDatabase();
        importStations(database);
        importDocks(database);
    }

    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        SQLiteDatabase db = dbHelperMap.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelperMap.TABLE_STATIONS, null, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String stationUID = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_STATION_UID));
                    String stationName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_STATION_NAME));
                    double positionLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_POSITION_LAT));
                    double positionLon = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_POSITION_LON));
                    int bikesCapacity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_BIKES_CAPACITY));
                    stations.add(new Station(stationUID, stationName, positionLat, positionLon, bikesCapacity));
                }
            } finally {
                cursor.close();
            }
        }
        return stations;
    }

    private void importStations(SQLiteDatabase db) {
        try {
            String json = readAsset("NTUStations.json");
            JSONArray stationsArray = new JSONArray(json);
            for (int i = 0; i < stationsArray.length(); i++) {
                JSONObject stationObject = stationsArray.getJSONObject(i);
                String stationUID = stationObject.getString("StationUID");
                String stationName = stationObject.getJSONObject("StationName").getString("Zh_tw");
                double positionLat = stationObject.getJSONObject("StationPosition").getDouble("PositionLat");
                double positionLon = stationObject.getJSONObject("StationPosition").getDouble("PositionLon");
                int bikesCapacity = stationObject.getInt("BikesCapacity");

                Cursor cursor = db.query(
                        DatabaseHelperMap.TABLE_STATIONS,
                        null,
                        DatabaseHelperMap.COLUMN_STATION_UID + "=?",
                        new String[]{stationUID},
                        null,
                        null,
                        null
                );
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.close();
                    continue;
                }

                ContentValues values = new ContentValues();
                values.put(DatabaseHelperMap.COLUMN_STATION_UID, stationUID);
                values.put(DatabaseHelperMap.COLUMN_STATION_NAME, stationName);
                values.put(DatabaseHelperMap.COLUMN_POSITION_LAT, positionLat);
                values.put(DatabaseHelperMap.COLUMN_POSITION_LON, positionLon);
                values.put(DatabaseHelperMap.COLUMN_BIKES_CAPACITY, bikesCapacity);
                db.insert(DatabaseHelperMap.TABLE_STATIONS, null, values);
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Failed to import stations: " + e.getMessage());
        }
    }

    private void importDocks(SQLiteDatabase db) {
        try {
            String json = readAsset("Docks.json");
            JSONArray docksArray = new JSONArray(json);
            for (int i = 0; i < docksArray.length(); i++) {
                JSONObject dockObject = docksArray.getJSONObject(i);
                String dockUID = dockObject.getString("DockUID");
                String dockID = dockObject.getString("DockID");
                String stationUID = dockObject.getString("StationUID");
                String bikeID = dockObject.getString("Bike");

                Cursor cursor = db.query(
                        DatabaseHelperMap.TABLE_DOCKS,
                        null,
                        DatabaseHelperMap.COLUMN_DOCK_UID + "=?",
                        new String[]{dockUID},
                        null,
                        null,
                        null
                );
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.close();
                    continue;
                }

                ContentValues values = new ContentValues();
                values.put(DatabaseHelperMap.COLUMN_DOCK_UID, dockUID);
                values.put(DatabaseHelperMap.COLUMN_DOCK_ID, dockID);
                values.put(DatabaseHelperMap.COLUMN_DOCK_STATION_UID, stationUID);
                values.put(DatabaseHelperMap.COLUMN_BIKE_ID, bikeID);
                db.insert(DatabaseHelperMap.TABLE_DOCKS, null, values);
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Failed to import docks: " + e.getMessage());
        }
    }

    private String readAsset(String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        try (InputStream is = assetManager.open(fileName)) {
            byte[] buffer = new byte[is.available()];
            int read = is.read(buffer);
            return new String(buffer, 0, read, StandardCharsets.UTF_8);
        }
    }
}
