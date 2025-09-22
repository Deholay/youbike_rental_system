package com.example.youbike;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ReportActivity extends AppCompatActivity {

    private EditText vehicleIdEditText;
    private Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        vehicleIdEditText = findViewById(R.id.vehicleIdEditText);
        reportButton = findViewById(R.id.ReportButton);
        copyFileFromAssets();



        reportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVehicleStatus("通報維修");
                Toast.makeText(ReportActivity.this, "Report Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void copyFileFromAssets() {
        File file = new File(getFilesDir(), "Bikes.json");
        if (!file.exists()) {
            try {
                InputStream is = getAssets().open("Bikes.json");
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

    public void updateVehicleStatus(String status) {
        try {
            File file = new File(getFilesDir(), "Bikes.json");
            FileInputStream fis = new FileInputStream(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            String json = new String(buffer, "UTF-8");
            JSONArray vehicles = new JSONArray(json);

            for (int i = 0; i < vehicles.length(); i++) {
                JSONObject vehicle = vehicles.getJSONObject(i);
                if (vehicle.getString("BikeUID").equals(vehicleIdEditText.getText().toString())) {
                    vehicle.put("Type", status); // 更新狀態
                    break;
                }
            }

            // 將更新後的 JSON 寫回檔案
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(vehicles.toString().getBytes("UTF-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


