package com.example.youbike;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowRegisteredUsersActivity extends AppCompatActivity {

    private TextView tvUserList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        tvUserList = findViewById(R.id.tvUserList);  // Ensure this ID matches your layout file

        dbHelper = new DatabaseHelper(this);

        showRegisteredUsers();
    }

    private void showRegisteredUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, email, phone, easycard, balance, password FROM users", null);

        StringBuilder users = new StringBuilder();
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String easycard = cursor.getString(cursor.getColumnIndexOrThrow("easycard"));
            double balance = cursor.getDouble(cursor.getColumnIndexOrThrow("balance"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            users.append("Username: ").append(username).append("\n")
                    .append("Email: ").append(email).append("\n")
                    .append("Password: ").append(password).append("\n")
                    .append("Phone: ").append(phone).append("\n")
                    .append("EasyCard: ").append(easycard).append("\n")
                    .append("Balance: ").append(balance).append("\n\n");
        }
        cursor.close();

        tvUserList.setText(users.toString());
    }
}
