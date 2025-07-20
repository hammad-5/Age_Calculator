package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.Age_Calculator_SoftSow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Feedback extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        ImageView arrow;
        arrow=findViewById(R.id.arrow_back);
// Inside your Activity's onCreate method
        Button submitButton = findViewById(R.id.submitButton);
        EditText feedbackComments = findViewById(R.id.feedbackComments);
        // 3. Find it and attach the listener
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);

        submitButton.setOnClickListener(v -> {
            String feedback = feedbackComments.getText().toString().trim();

            // Enhanced validation
            if (feedback.isEmpty()) {
                feedbackComments.setError(getString(R.string.error_empty_feedback));
                feedbackComments.requestFocus();
                return;
            }


            if (feedback.length() < 10) {
                feedbackComments.setError(getString(R.string.error_short_feedback));
                feedbackComments.requestFocus();
                return;
            }


            // Sanitize input
            String sanitizedFeedback = feedback.replaceAll("[<>]", "");

            try {
                String emailAddress = "hammadirshad38@gmail.com";
                String subject = getString(R.string.subject_feedback);

                // Secure intent creation
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, sanitizedFeedback);

                // Verify and launch intent
                if (getPackageManager().resolveActivity(emailIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send feedback using..."));
                    feedbackComments.setText(""); // Clear input after submission
                    Toast.makeText(this, getString(R.string.feedback_ready), Toast.LENGTH_SHORT).show();
                } else {
                    showNoEmailClientError();
                }
            } catch (SecurityException e) {
                Log.e("Feedback", "Security exception: " + e.getMessage());
                Toast.makeText(this, "Permission denied for email operation", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Feedback", "Error: " + e.getMessage());
                Toast.makeText(this, "Error sending feedback", Toast.LENGTH_SHORT).show();
            }
        });


//        EditText feedbackComments = findViewById(R.id.feedbackComments);
//        Button submitButton = findViewById(R.id.submitButton);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String feedback = feedbackComments.getText().toString().trim();
//
//                if (!feedback.isEmpty()) {
//                    // Show animation when submitted
//                    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
//                            submitButton,
//                            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f),
//                            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f),
//                            PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f, 1f)
//                    );
//                    animator.setDuration(500);
//                    animator.start();
//
//                    // Show toast or any success message
//                    Toast.makeText(Feedback.this, "Your Feedback Submitted!", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Feedback.this, "Firstly Write Some Text!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Feedback.this,dashboard.class);
                startActivity(intent);
            }
        });

    }


    private void showNoEmailClientError() {
        new AlertDialog.Builder(this)
                .setTitle("No Email Client")
                .setMessage("Please install an email client to send feedback")
                .setPositiveButton("OK", null)
                .show();
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

