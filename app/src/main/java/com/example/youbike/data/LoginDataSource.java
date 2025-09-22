package com.example.youbike.data;

import static android.util.Log.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.youbike.data.model.LoggedInUser;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private SQLiteDatabase database;
    public LoginDataSource(SQLiteDatabase database) {
        this.database = database;
    }

    public String getUsernameByEmail(String email) {
        String username = null;
        Cursor cursor = null;
        try {
            // 建立查询
            String[] projection = {"username", "phone","easycard"};
            String selection = "email = ?";
            String[] selectionArgs = {email};

            // 执行数据库查询
            cursor = database.query("users", projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // 获取第一行第一列的数据
                username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            }
        } catch (Exception e) {
            e("LoginDataSource", "Error while getting username");
        } finally {
            if (cursor != null) {
                cursor.close(); // 关闭游标防止内存泄露
            }
        }
        return username;
    }

    public String getPhoneByEmail(String email) {
        String phone = null;
        Cursor cursor = null;
        try {
            // 建立查询
            String[] projection = {"phone"};
            String selection = "email = ?";
            String[] selectionArgs = {email};

            // 执行数据库查询
            cursor = database.query("users", projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // 获取第一行第一列的数据
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            }
        } catch (Exception e) {
            e("LoginDataSource", "Error while getting username");
        } finally {
            if (cursor != null) {
                cursor.close(); // 关闭游标防止内存泄露
            }
        }
        return phone;
    }

    public String getEasycardByEmail(String email) {
        String easycard = null;
        Cursor cursor = null;
        try {
            // 建立查询
            String[] projection = {"easycard"};
            String selection = "email = ?";
            String[] selectionArgs = {email};

            // 执行数据库查询
            cursor = database.query("users", projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // 获取第一行第一列的数据
                easycard = cursor.getString(cursor.getColumnIndexOrThrow("easycard"));
            }
        } catch (Exception e) {
            e("LoginDataSource", "Error while getting username");
        } finally {
            if (cursor != null) {
                cursor.close(); // 关闭游标防止内存泄露
            }
        }
        return easycard;
    }

    public double getBalanceByEmail(String email) {
        double balance = 0.0;
        Cursor cursor = null;
        try {
            // 建立查询
            String[] projection = {"balance"};
            String selection = "email = ?";
            String[] selectionArgs = {email};

            // 执行数据库查询
            cursor = database.query("users", projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // 获取第一行第一列的数据
                balance = cursor.getDouble(cursor.getColumnIndexOrThrow("balance"));
            }
        } catch (Exception e) {
            e("LoginDataSource", "Error while getting username");
        } finally {
            if (cursor != null) {
                cursor.close(); // 关闭游标防止内存泄露
            }
        }
        return balance;
    }
    public boolean updateBalance(String email, double newBalance) {
        ContentValues values = new ContentValues();
        values.put("balance", newBalance);
        try {
            int rowsUpdated = database.update("users", values, "email = ?", new String[]{email});
            if (rowsUpdated > 0) {
                Log.d("LoginDataSource", "Balance updated successfully.");
                return true;
            } else {
                Log.e("LoginDataSource", "No rows updated, check your query.");
                return false;
            }
        } catch (Exception e) {
            Log.e("LoginDataSource", "Error updating balance: " + e.getMessage());
            return false;
        }
    }






    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "11",
                            "Jane Doe",
                            "123456789",
                            1.0,
                            "0000");

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
