package com.example.youbike;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VehicleDispatchActivity extends AppCompatActivity {

    private EditText DockIdEditText;
    private TextView dockStatusTextView;
    private Button queryDockButton;
    private Button AddButton;
    private Button RemoveButton;
    private EditText BikeIDText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_dispatch);
        copyFileFromAssets();

        DockIdEditText = findViewById(R.id.DockIdEditText);
        dockStatusTextView = findViewById(R.id.dockStatusTextView);
        queryDockButton = findViewById(R.id.queryDockButton);
        AddButton = findViewById(R.id.AddButton);
        RemoveButton = findViewById(R.id.RemoveButton);
        BikeIDText  =findViewById(R.id.BikeIDText);


        queryDockButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String DockId = DockIdEditText.getText().toString();
                queryDockStatus(DockId);
            }
        });

        AddButton.setOnClickListener(v -> updateDock("add"));
        RemoveButton.setOnClickListener(v -> updateDock("remove"));
    }

    private void copyFileFromAssets() {
        File file = new File(getFilesDir(), "Docks.json");
        if (!file.exists()) {
            try {
                InputStream is = getAssets().open("Docks.json");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateDock(String action) {
        String dockId = DockIdEditText.getText().toString();
        String bikeId = BikeIDText.getText().toString();

        try {
            File file = new File(getFilesDir(), "Docks.json");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonStr = new String(data, "UTF-8");
            JSONArray docks = new JSONArray(jsonStr);

            // 檢查新增時車輛是否已經存在於其他 Dock
            if ("add".equals(action)) {
                for (int i = 0; i < docks.length(); i++) {
                    JSONObject dock = docks.getJSONObject(i);
                    String existingBike = dock.getString("Bike");
                    if (bikeId.equals(existingBike)) {
                        dockStatusTextView.setText("Bike " + bikeId + " is already docked.");
                        return; // 如果車輛已經存在，停止執行
                    }
                }
            }

            // 更新或移除車輛
            for (int i = 0; i < docks.length(); i++) {
                JSONObject dock = docks.getJSONObject(i);
                if (dock.getString("DockUID").equals(dockId)) {
                    if ("add".equals(action)) {
                        dock.put("Bike", bikeId);
                    } else if ("remove".equals(action)) {
                        dock.put("Bike", ""); // 設為空白當移除
                    }
                    dockStatusTextView.setText("Dock Updated: " + dock.toString());
                    break;
                }
            }

            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(docks.toString().getBytes("UTF-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            dockStatusTextView.setText("Error updating dock: " + e.getMessage());
        }
    }


    private void queryDockStatus(String DockID) {
        try {
            File file = new File(getFilesDir(), "Docks.json"); // 從內部存儲讀取檔案
            FileInputStream fis = new FileInputStream(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            String json = new String(buffer, "UTF-8");
            JSONArray vehicles = new JSONArray(json); // 直接解析成 JSONArray

            for (int i = 0; i < vehicles.length(); i++) {
                JSONObject vehicle = vehicles.getJSONObject(i);
                if (vehicle.getString("DockUID").equals(DockID)) { // 使用正確的鍵名
                    String bike = vehicle.getString("Bike"); // 使用正確的鍵名
                    dockStatusTextView.setText("Bike: " + bike); // 顯示車輛類型
                    return;
                }
            }
            dockStatusTextView.setText("No vehicle found with ID: " + DockID);
        } catch (Exception e) {
            e.printStackTrace();
            dockStatusTextView.setText("Error parsing");
        }
    }
}


