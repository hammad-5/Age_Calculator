package com.Age_Calculator.Age_Calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.Age_Calculator.R;
import com.example.hijrimonth_find.R;

public class MainActivity extends AppCompatActivity {
    private TextView ageTextView;
    private String text = "AGE CALCULATOR";
    private int index = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set background color before setting content view
        getWindow().getDecorView().setBackgroundColor(
                ContextCompat.getColor(this, R.color.Grullo)
        );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, dashboard.class);
                startActivity(mainIntent);
                finish();
            }
        }, 4000);


        ageTextView = findViewById(R.id.age_calculate);

        // Start Text Animation
        startTextAnimation();

    }
    private void startTextAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    ageTextView.setText(text.substring(0, index + 1)); // Show text letter by letter
                    index++;
                    handler.postDelayed(this, 200); // Delay between letters
                } else {
                    animateColorChange(); // Start color animation after full text appears
                }
            }
        }, 200);
    }

    private void animateColorChange() {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(ageTextView, "textColor",
                Color.RED, Color.parseColor("#5B6C9A"), Color.CYAN, Color.MAGENTA, Color.GREEN);
        colorAnim.setDuration(2000); // Smooth transition time
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatMode(ObjectAnimator.REVERSE);
        colorAnim.setRepeatCount(ObjectAnimator.INFINITE);
        colorAnim.start();
    }

}