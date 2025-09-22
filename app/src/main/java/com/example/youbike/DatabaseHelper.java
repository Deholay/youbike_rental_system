package com.example.youbike;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "SignLog.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "SignLog.db", null, 2);  // Increment version number
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE users (email TEXT PRIMARY KEY, password TEXT, username TEXT, phone TEXT, easycard TEXT, balance REAL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            MyDB.execSQL("ALTER TABLE users ADD COLUMN balance REAL DEFAULT 0");
        }
    }

    public Boolean insertData(String email, String password, String username, String phone, String easycard, double balance) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("easycard", easycard);
        contentValues.put("balance", balance);
        long result = MyDatabase.insert("users", null, contentValues);

        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        return cursor.getCount() > 0;
    }

    public Boolean checkMaintenancePassword(String password) {
        return Objects.equals(password, "6666");
    }


    public boolean updateBalance(String email, double newBalance) {
        ContentValues values = new ContentValues();
        values.put("balance", newBalance);
        try {
            int rowsUpdated = getWritableDatabase().update("users", values, "email = ?", new String[]{email});
            if (rowsUpdated > 0) {
                Log.d("Databasehelper", "Balance updated successfully.");
                return true;
            } else {
                Log.e("DatabaseHelper", "No rows updated, check your query.");
                return false;
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error updating balance: " + e.getMessage());
            return false;
        }
    }

    public double getBalanceByEmail(String email) {
        double balance = 0.0;  // Default balance, you had it as 100.0 initially
        Cursor cursor = null;
        try {
            // Building the query
            String[] projection = {"balance"};
            String selection = "email = ?";
            String[] selectionArgs = {email};

            // Executing the query
            cursor = getReadableDatabase().query("users", projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Getting the balance from the first row
                balance = cursor.getDouble(cursor.getColumnIndexOrThrow("balance"));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while getting balance: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();  // Closing the cursor to prevent memory leaks
            }
        }
        return balance;
    }
}
