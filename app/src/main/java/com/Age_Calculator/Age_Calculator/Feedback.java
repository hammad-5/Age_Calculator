package com.Age_Calculator.Age_Calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hijrimonth_find.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Feedback extends AppCompatActivity {
    NavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageView arrow;
        arrow=findViewById(R.id.arrow_back);

        EditText feedbackComments = findViewById(R.id.feedbackComments);
        Button submitButton = findViewById(R.id.submitButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackComments.getText().toString().trim();

                if (!feedback.isEmpty()) {
                    // Show animation when submitted
                    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                            submitButton,
                            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f),
                            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f),
                            PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f, 1f)
                    );
                    animator.setDuration(500);
                    animator.start();

                    // Show toast or any success message
                    Toast.makeText(Feedback.this, "Your Feedback Submitted!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Feedback.this, "Firstly Write Some Text!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Feedback.this,dashboard.class);
                startActivity(intent);
            }
        });

    }

    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            startActivity(new Intent(Feedback.this, dashboard.class));
            return true;
        } else if (itemId == R.id.about_ic) {
            startActivity(new Intent(Feedback.this, about_us.class));
            return true;
        } else if (itemId == R.id.privacy) {
            startActivity(new Intent(Feedback.this, privacy_policy.class));
            return true;
        } else if (itemId == R.id.setting_ic) {
            startActivity(new Intent(Feedback.this, setting.class));

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