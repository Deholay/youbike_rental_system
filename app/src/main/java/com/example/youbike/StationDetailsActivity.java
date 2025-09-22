package com.example.youbike;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youbike.data.LoginDataSource;

import java.util.ArrayList;
import java.util.List;

public class StationDetailsActivity extends AppCompatActivity {
    private List<Dock> dockList = new ArrayList<>();
    private String stationUID;
    private DatabaseHelper dbHelper;
    private DatabaseHelperMap dbHelperMap;
    private DockAdapter dockAdapter;
    private LoginDataSource loginDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        dbHelperMap = new DatabaseHelperMap(this);
        stationUID = getIntent().getStringExtra("stationUID");
        dbHelper = new DatabaseHelper(this);
        loginDataSource = new LoginDataSource(dbHelper.getReadableDatabase());


        TextView stationNameTextView = findViewById(R.id.station_name);
        TextView stationUIDTextView = findViewById(R.id.station_UID);

        String email = getIntent().getStringExtra("USER_EMAIL");


        // Get the station details from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String stationUID = extras.getString("stationUID");

            // Set the station details to the text views
            stationUIDTextView.setText(stationUID);
        }

        setupRecyclerView();
        parseJSON();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.docks_recycler_view);
        dockAdapter = new DockAdapter(this, dockList, dbHelper);
        recyclerView.setAdapter(dockAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void parseJSON() {
        SQLiteDatabase db = dbHelperMap.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelperMap.TABLE_DOCKS, null, DatabaseHelperMap.COLUMN_DOCK_STATION_UID + "=?",
                new String[]{stationUID}, null, null, null);

        if (cursor != null) {
            dockList.clear();
            while (cursor.moveToNext()) {
                String dockUID = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_DOCK_UID));
                String dockID = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_DOCK_ID));
                String bikeID = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_BIKE_ID));
                String bikeStatus = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperMap.COLUMN_BIKE_STATUS));

                Dock dock = new Dock(dockUID, stationUID, dockID, bikeID, bikeStatus);
                dockList.add(dock);
            }
            cursor.close();
            dockAdapter.notifyDataSetChanged();
        }
    }

    public void rentBike(String dockUID, String bikeID) {
        if (RentBikeStatus.isIsRenting()) {
            Toast.makeText(this, "你已經租借了一台Youbike！", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelperMap.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperMap.COLUMN_BIKE_STATUS, "rented");
        values.put(DatabaseHelperMap.COLUMN_BIKE_ID, "");
        RentBikeStatus.setBikeIDNowImRenting(bikeID);

        int rows = db.update(DatabaseHelperMap.TABLE_DOCKS, values, DatabaseHelperMap.COLUMN_DOCK_UID + "=?", new String[]{dockUID});

        if (rows > 0) {
            Toast.makeText(this, "YouBike租借成功！", Toast.LENGTH_SHORT).show();
            RentBikeStatus.setIsRenting(true);
        } else {
            Toast.makeText(this, "YouBike租借失敗！", Toast.LENGTH_SHORT).show();
        }

        parseJSON();
    }

    public void returnBike(String dockUID, String bikeID) {
        if (!RentBikeStatus.isIsRenting()) {
            Toast.makeText(this, "你還沒有租借Youbike！", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelperMap.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperMap.COLUMN_BIKE_STATUS, "available");
        values.put(DatabaseHelperMap.COLUMN_BIKE_ID, RentBikeStatus.getBikeIDNowImRenting());

        int rows = db.update(DatabaseHelperMap.TABLE_DOCKS, values, DatabaseHelperMap.COLUMN_DOCK_UID + "=?", new String[]{dockUID});

        if (rows > 0) {
            Toast.makeText(this, "Youbike歸還成功！", Toast.LENGTH_SHORT).show();
            RentBikeStatus.setIsRenting(false);
        } else {
            Toast.makeText(this, "Youbike歸還失敗！", Toast.LENGTH_SHORT).show();
        }

        parseJSON();
    }
    public String getCurrentUserEmail() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("USER_EMAIL");
        return email;
    }


    public void deductBalance(String email, double amount) {
        double currentBalance = dbHelper.getBalanceByEmail(email);
        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            dbHelper.updateBalance(email, newBalance);
            Toast.makeText(this, "餘額已更新，新餘額：" + newBalance, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "餘額不足，無法扣款", Toast.LENGTH_SHORT).show();
        }
    }

}
