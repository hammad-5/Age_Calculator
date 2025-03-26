package com.Age_Calculator_SoftSow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.Age_Calculator_SoftSow.Age_Calculator_SoftSow.MainActivity;

import java.util.Locale;

public class translate extends AppCompatActivity {


    private Spinner languageSpinner;
    private Button applyButton;
    private String selectedLanguage = "en"; // Default language


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        languageSpinner = findViewById(R.id.language_spinner);
        applyButton = findViewById(R.id.btn_apply_language);

        // Define language options
        String[] languages = {"English", "Spanish", "French", "German", "Chinese"};
        final String[] languageCodes = {"en", "es", "fr", "de", "zh"};

        // Set up spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Get the selected language
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languageCodes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Apply button to set the language
        applyButton.setOnClickListener(v -> {
            setLocale(selectedLanguage);
            finish(); // Return to previous activity
        });
    }

    // Change app language
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save the chosen language in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", languageCode);
        editor.apply();

        // Restart the app to apply changes
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Load saved language on app start
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en");
        setLocale(language);
    }
}
