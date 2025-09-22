package com.example.youbike;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CrossAreaVehiclesActivity extends AppCompatActivity {

    private ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_area_vehicles);

        resultsListView = findViewById(R.id.ResultsListView);

        checkAndDisplayAnomalies();
    }

    private void checkAndDisplayAnomalies() {
        DockChecker dockChecker = new DockChecker(this);
        ArrayList<String> anomalies = dockChecker.checkDocksForAnomalies();

        // 使用 ArrayAdapter 來顯示列表數據
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, anomalies);
        resultsListView.setAdapter(adapter);

        if (anomalies.isEmpty()) {
            adapter.add("所有車輛均符合跨區規則。");
        }
    }
}
