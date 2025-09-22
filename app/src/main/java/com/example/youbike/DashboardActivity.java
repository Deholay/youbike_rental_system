package com.example.youbike;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnVehicleStatus = findViewById(R.id.btnVehicleStatus);
        Button btnCrossAreaVehicles = findViewById(R.id.btnCrossAreaVehicles);
        Button btnVehicleDispatch = findViewById(R.id.btnVehicleDispatch);
        Button btnShowRegisteredUsers = findViewById(R.id.btnShowRegisteredUsers);


        btnVehicleStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, VehicleStatusActivity.class);
                startActivity(intent);
            }
        });

        btnCrossAreaVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CrossAreaVehiclesActivity.class);
                startActivity(intent);
            }
        });

        btnVehicleDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, VehicleDispatchActivity.class);
                startActivity(intent);
            }
        });
        btnShowRegisteredUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ShowRegisteredUsersActivity.class);
                startActivity(intent);
            }
        });

        // 類似地為其他按鈕設置事件
    }
}
