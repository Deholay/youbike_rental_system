package com.example.youbike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.youbike.databinding.ActivityMaintenanceLoginBinding;

public class MaintenanceLoginActivity extends AppCompatActivity {

    ActivityMaintenanceLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaintenanceLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.maintenanceLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = binding.maintenanceLoginPassord.getText().toString();

                if(password.equals(""))
                    Toast.makeText(MaintenanceLoginActivity.this, "Please fill in the password", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkMaintenancePassword(password);

                    if(checkCredentials == true){
                        Toast.makeText(MaintenanceLoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MaintenanceLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MaintenanceLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}