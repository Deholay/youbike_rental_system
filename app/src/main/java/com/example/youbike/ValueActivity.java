// ValueActivity.java

package com.example.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youbike.ui.value.ValueFragment;

public class ValueActivity extends AppCompatActivity {

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);

        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        if (savedInstanceState == null) {
            ValueFragment valueFragment = new ValueFragment();

            Bundle bundle = new Bundle();
            bundle.putString("USER_EMAIL", userEmail);
            valueFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, valueFragment)
                    .commit();
        }
    }

}
