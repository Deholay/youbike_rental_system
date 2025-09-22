package com.example.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youbike.data.LoginDataSource;
import com.example.youbike.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewEmail, textViewPhone, textViewEasyCard, textViewBalance;
    private LoginDataSource loginDataSource;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        // 初始化 TextViews
        textViewUsername = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewEasyCard = findViewById(R.id.textViewEasyCard);
        textViewBalance = findViewById(R.id.textViewBalance);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        loginDataSource = new LoginDataSource(dbHelper.getReadableDatabase());

        // 從 Intent 獲取電子郵件
        Intent intent = getIntent();
        String email = intent.getStringExtra("USER_EMAIL");

        if (email != null) {
            showUserProfile(email);
        } else {
            textViewUsername.setText("No email found.");
        }
    }

    private void showUserProfile(String email) {
        String username = loginDataSource.getUsernameByEmail(email);
        String phone = loginDataSource.getPhoneByEmail(email);
        String easycard = loginDataSource.getEasycardByEmail(email);
        double balance = loginDataSource.getBalanceByEmail(email);

        if (username != null) {
            textViewUsername.setText("Username: " + username);
        } else {
            textViewUsername.setText("Username not found.");
        }

        textViewEmail.setText("Email: " + email);

        if (phone != null) {
            textViewPhone.setText("Phone: " + phone);
        } else {
            textViewPhone.setText("Phone not found.");
        }

        if (easycard != null) {
            textViewEasyCard.setText("EasyCard: " + easycard);
        } else {
            textViewEasyCard.setText("EasyCard not found.");
        }

        textViewBalance.setText("Balance: " + balance);
    }
}
