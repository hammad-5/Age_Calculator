package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.Age_Calculator_SoftSow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class setting extends AppCompatActivity {
    NavigationView bottomNavigationView;
    ImageView arrow_bac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // Bottom Navigation Setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        arrow_bac=findViewById(R.id.arrow_back);
        arrow_bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(setting.this,dashboard.class);
                startActivity(intent);
            }
        });

    }

    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            startActivity(new Intent(setting.this, dashboard.class));
            return true;
        } else if (itemId == R.id.about_ic) {
            startActivity(new Intent(setting.this, about_us.class));
            return true;
        } else if (itemId == R.id.privacy) {
            startActivity(new Intent(setting.this, privacy_policy.class));
            return true;
        } else if (itemId == R.id.setting_ic) {
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Closes all activities in the task and exits the app
    }


   }

