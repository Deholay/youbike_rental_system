package com.example.youbike;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class NavigationHandler implements NavigationView.OnNavigationItemSelectedListener {

    private final Context context;
    private final String userEmail;
    private final NavController navController;

    public NavigationHandler(Context context, String userEmail, NavController navController) {
        this.context = context;
        this.userEmail = userEmail;
        this.navController = navController;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.profileButton) {
            Intent profileIntent = new Intent(context, ProfileActivity.class);
            profileIntent.putExtra("USER_EMAIL", userEmail);
            context.startActivity(profileIntent);
            return true;
        }
        if (id == R.id.ReportButton) {
            context.startActivity(new Intent(context, ReportActivity.class));
            return true;
        }
        if (id == R.id.valueButton) {
            Intent valueIntent = new Intent(context, ValueActivity.class);
            valueIntent.putExtra("USER_EMAIL", userEmail);
            context.startActivity(valueIntent);
            return true;
        }
        return NavigationUI.onNavDestinationSelected(menuItem, navController);
    }
}
