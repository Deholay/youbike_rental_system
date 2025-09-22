package com.example.youbike.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperMap extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bike_rental.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_STATIONS = "stations";
    public static final String COLUMN_STATION_UID = "station_uid";
    public static final String COLUMN_STATION_NAME = "station_name";
    public static final String COLUMN_POSITION_LAT = "position_lat";
    public static final String COLUMN_POSITION_LON = "position_lon";
    public static final String COLUMN_BIKES_CAPACITY = "bikes_capacity";

    public static final String TABLE_DOCKS = "docks";
    public static final String COLUMN_DOCK_UID = "dock_uid";
    public static final String COLUMN_DOCK_ID = "dock_id";
    public static final String COLUMN_DOCK_STATION_UID = "dock_station_uid";
    public static final String COLUMN_BIKE_STATUS = "bike_status";
    public static final String COLUMN_BIKE_ID = "bike_id";

    public DatabaseHelperMap(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATIONS_TABLE = "CREATE TABLE " + TABLE_STATIONS + "("
                + COLUMN_STATION_UID + " TEXT PRIMARY KEY,"
                + COLUMN_STATION_NAME + " TEXT,"
                + COLUMN_POSITION_LAT + " REAL,"
                + COLUMN_POSITION_LON + " REAL,"
                + COLUMN_BIKES_CAPACITY + " INTEGER" + ")";
        db.execSQL(CREATE_STATIONS_TABLE);

        String CREATE_DOCKS_TABLE = "CREATE TABLE " + TABLE_DOCKS + "("
                + COLUMN_DOCK_UID + " TEXT PRIMARY KEY,"
                + COLUMN_DOCK_ID + " TEXT,"
                + COLUMN_DOCK_STATION_UID + " TEXT,"
                + COLUMN_BIKE_STATUS + " TEXT,"
                + COLUMN_BIKE_ID + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_DOCK_STATION_UID + ") REFERENCES " + TABLE_STATIONS + "(" + COLUMN_STATION_UID + "))";
        db.execSQL(CREATE_DOCKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCKS);
        onCreate(db);
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCKS);
        onCreate(db);
    }
}
