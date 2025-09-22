package com.example.youbike;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, phoneEditText, easycardEditText, passwordEditText, confirmEditText;
    private Button signupButton;
    private TextView loginRedirectText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.signup_username);
        emailEditText = findViewById(R.id.signup_email);
        phoneEditText = findViewById(R.id.signup_phone);
        easycardEditText = findViewById(R.id.signup_easycard);
        passwordEditText = findViewById(R.id.signup_password);
        confirmEditText = findViewById(R.id.signup_confirm);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        dbHelper = new DatabaseHelper(this);

        signupButton.setOnClickListener(v -> registerUser());

        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String easycard = easycardEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(easycard) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("phone", phone);
        values.put("easycard", easycard);
        values.put("password", password);

        long newRowId = db.insert("users", null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
