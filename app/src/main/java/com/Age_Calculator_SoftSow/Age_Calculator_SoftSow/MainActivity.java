package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.Age_Calculator_SoftSow.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    private TextView ageTextView;
    private String text = "AGE CALCULATOR";
    private int index = 0;
    private Handler handler = new Handler();

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        text = getString(R.string.age_calculator_title);

        // Set background color before setting content view
        getWindow().getDecorView().setBackgroundColor(
                ContextCompat.getColor(this, R.color.Grullo)
        );
        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
        loadLocale();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, dashboard.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);


        ageTextView = findViewById(R.id.age_calculate);

        // Start Text Animation
//        startTextAnimation();

        // Change status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.LIGHT_GOLDEN));
        }

    }

    private void loadLocale() {
        String language = sharedPreferences.getString("My_Lang", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

//
//    private void startTextAnimation() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (index < text.length()) {
//                    ageTextView.setText(text.substring(0, index + 1)); // Show text letter by letter
//                    index++;
//                    handler.postDelayed(this, 200); // Delay between letters
//                } else {
//                    animateColorChange(); // Start color animation after full text appears
//                }
//            }
//        }, 200);
//    }
//
//    private void animateColorChange() {
//        ObjectAnimator colorAnim = ObjectAnimator.ofInt(ageTextView, "textColor",
//                Color.RED, Color.parseColor("#5B6C9A"), Color.CYAN, Color.MAGENTA, Color.GREEN);
//        colorAnim.setDuration(2000); // Smooth transition time
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatMode(ObjectAnimator.REVERSE);
//        colorAnim.setRepeatCount(ObjectAnimator.INFINITE);
//        colorAnim.start();
//    }

    // Call this function with true = dark mode, false = light mode
    public void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}