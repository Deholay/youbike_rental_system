package com.example.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youbike.data.model.LoggedInUser;
import com.example.youbike.data.LoginDataSource;
import com.example.youbike.databinding.ActivityLogin2Binding;

public class LoginActivity extends AppCompatActivity {

    ActivityLogin2Binding binding;
    DatabaseHelper databaseHelper;
    private LoginDataSource loginDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        loginDataSource = new LoginDataSource(databaseHelper.getReadableDatabase());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    if(checkCredentials){
                        String username = loginDataSource.getUsernameByEmail(email);
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USER_EMAIL", email);
                        intent.putExtra("USER_NAME", username); // 如果有用户名，可以从数据库中获取并传递
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.dashboardRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MaintenanceLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
