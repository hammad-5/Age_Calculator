package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.Age_Calculator_SoftSow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.util.Calendar;
import java.util.Locale;

public class dashboard extends AppCompatActivity {

    EditText birthdat, currentdat;
    TextView cyear, cmonth, cdays, load_lis;
    TextView cryea, crmont, crwee, crday, crhour, crminute, crsecon;
    Button calculateButton, clearButton;
    Calendar birthCalendar, currentCalendar;
    TextView zsign, zmonth, zchinese;
    private SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    NavigationView navigationview;
    ImageView menuIco;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
        loadLocale();
        setContentView(R.layout.activity_dashboard);

        birthdat = findViewById(R.id.birthDate);
        currentdat = findViewById(R.id.currentdate);

        cyear = findViewById(R.id.cyear);
        cmonth = findViewById(R.id.cmonth);
        cdays = findViewById(R.id.cdays);
        cryea = findViewById(R.id.cryear);
        crmont = findViewById(R.id.crmonth);
        crwee = findViewById(R.id.crweek);
        crday = findViewById(R.id.crdays);
        crhour = findViewById(R.id.crhours);
        crminute = findViewById(R.id.crminutes);
        crsecon = findViewById(R.id.crseconds);
        calculateButton = findViewById(R.id.btn);
        clearButton = findViewById(R.id.btn_clear);

        load_lis = findViewById(R.id.load_list);
        load_lis.setPaintFlags(load_lis.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        load_lis.setOnClickListener(view -> {
            Intent intent = new Intent(dashboard.this, about_us.class);
            startActivity(intent);
            finish();
        });

        zsign = findViewById(R.id.zsign);
        zmonth = findViewById(R.id.zmonth);
        zchinese = findViewById(R.id.zchinese);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationview = findViewById(R.id.navig_bar);

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        menuIco = findViewById(R.id.menuIcon);
        menuIco.setOnClickListener(view -> drawerLayout.openDrawer(navigationview));

        navigationview.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.feed) {
                startActivity(new Intent(dashboard.this, Feedback.class));
                finish();
                drawerLayout.closeDrawers();
                return true;
            } else if (item.getItemId() == R.id.translate) {
                showLanguageDialog();
                drawerLayout.closeDrawers();
                return true;
            } else if (item.getItemId() == R.id.darkMood) {
                showThemeAlert();  // Now shows disabled message
                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);

        birthdat.setFocusable(false);
        birthdat.setClickable(false);
        currentdat.setFocusable(false);
        currentdat.setClickable(false);

        birthCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();

        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);
        currentdat.setText(day + " - " + (month + 1) + " - " + year);

        birthdat.setOnClickListener(v -> showDatePickerDialog(birthdat));
        currentdat.setOnClickListener(v -> showDatePickerDialog(currentdat));

        calculateButton.setOnClickListener(v -> {
            if (birthdat.getText().toString().isEmpty() || currentdat.getText().toString().isEmpty()) {
                new AlertDialog.Builder(dashboard.this)
                        .setTitle(getString(R.string.error_title))
                        .setMessage(getString(R.string.error_message_birth_date))
                        .setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                int birthYear = birthCalendar.get(Calendar.YEAR);
                int birthMonth = birthCalendar.get(Calendar.MONTH);
                int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);
                calculateAge(birthYear, birthMonth, birthDay);
            }
        });

        clearButton.setOnClickListener(v -> clearAllFields());

        birthdat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableRight = birthdat.getCompoundDrawables()[2];
                if (drawableRight != null &&
                        event.getRawX() >= (birthdat.getRight() - drawableRight.getBounds().width())) {
                    showDatePickerDialog(birthdat);
                    return true;
                }
            }
            return false;
        });

        currentdat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableRight = currentdat.getCompoundDrawables()[2];
                if (drawableRight != null &&
                        event.getRawX() >= (currentdat.getRight() - drawableRight.getBounds().width())) {
                    showDatePickerDialog(currentdat);
                    return true;
                }
            }
            return false;
        });
    }

    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            return true;
        } else if (itemId == R.id.about_ic) {
            startActivity(new Intent(dashboard.this, about_us.class));
            return true;
        } else if (itemId == R.id.privacy) {
            startActivity(new Intent(dashboard.this, privacy_policy.class));
            return true;
        } else if (itemId == R.id.setting_ic) {
            startActivity(new Intent(dashboard.this, setting.class));
            return true;
        }
        return false;
    }

    private void showLanguageDialog() {
        final String[] languages = {"English", "Urdu", "German"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(languages, -1, (dialogInterface, i) -> {
            String languageCode;
            switch (i) {
                case 0: languageCode = "en"; break;
                case 1: languageCode = "ur"; break;
                case 2: languageCode = "de"; break;
                default: languageCode = "en";
            }
            setLocale(languageCode);
            dialogInterface.dismiss();
            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
        });
        builder.create().show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    private void loadLocale() {
        String language = sharedPreferences.getString("My_Lang", "en");
        setLocale(language);
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(dashboard.this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + " - " + (selectedMonth + 1) + " - " + selectedYear;
            editText.setText(selectedDate);

            if (editText == birthdat) {
                birthCalendar.set(selectedYear, selectedMonth, selectedDay);
            } else if (editText == currentdat) {
                currentCalendar.set(selectedYear, selectedMonth, selectedDay);
            }
        }, year, month, day).show();
    }

    private void calculateAge(int birthYear, int birthMonth, int birthDay) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(birthYear, birthMonth, birthDay, 0, 0, 0);

        long diffMillis = today.getTimeInMillis() - birthDate.getTimeInMillis();
        long totalSeconds = diffMillis / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;
        long totalDays = totalHours / 24;
        long totalWeeks = totalDays / 7;

        int years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        int months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH);
        int days = today.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH);

        if (days < 0) {
            months--;
            days += 30;
        }
        if (months < 0) {
            years--;
            months += 12;
        }

        int totalMonths = years * 12 + months;

        cyear.setText(String.valueOf(years));
        cmonth.setText(String.valueOf(totalMonths));
        cdays.setText(String.valueOf(totalDays));
        cryea.setText(String.valueOf(years));
        crmont.setText(String.valueOf(totalMonths));
        crwee.setText(String.valueOf(totalWeeks));
        crday.setText(String.valueOf(totalDays));
        crhour.setText(String.valueOf(totalHours));
        crminute.setText(String.valueOf(totalMinutes));
        crsecon.setText(String.valueOf(totalSeconds));

        zsign.setText(getZodiacSign(birthMonth + 1, birthDay));
        zmonth.setText(getMonthName(birthMonth + 1));
        zchinese.setText(getChineseZodiac(birthYear));
    }

    private String getMonthName(int month) {
        int[] monthResIds = {
                R.string.month_jan, R.string.month_feb, R.string.month_mar, R.string.month_apr,
                R.string.month_may, R.string.month_jun, R.string.month_jul, R.string.month_aug,
                R.string.month_sep, R.string.month_oct, R.string.month_nov, R.string.month_dec
        };
        return (month >= 1 && month <= 12) ? getString(monthResIds[month - 1]) : getString(R.string.month_unknown);
    }

    private String getChineseZodiac(int year) {
        int baseYear = 1900;
        int index = (year - baseYear) % 12;
        if (index < 0) index += 12;
        String[] zodiac = {
                getString(R.string.zodiac_rat), getString(R.string.zodiac_ox), getString(R.string.zodiac_tiger),
                getString(R.string.zodiac_rabbit), getString(R.string.zodiac_dragon), getString(R.string.zodiac_snake),
                getString(R.string.zodiac_horse), getString(R.string.zodiac_goat), getString(R.string.zodiac_monkey),
                getString(R.string.zodiac_rooster), getString(R.string.zodiac_dog), getString(R.string.zodiac_pig)
        };
        return zodiac[index];
    }

    private String getZodiacSign(int month, int day) {
        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) return getString(R.string.zodiac_aquarius);
        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) return getString(R.string.zodiac_pisces);
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) return getString(R.string.zodiac_aries);
        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) return getString(R.string.zodiac_taurus);
        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) return getString(R.string.zodiac_gemini);
        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) return getString(R.string.zodiac_cancer);
        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) return getString(R.string.zodiac_leo);
        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) return getString(R.string.zodiac_virgo);
        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) return getString(R.string.zodiac_libra);
        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) return getString(R.string.zodiac_scorpio);
        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) return getString(R.string.zodiac_sagittarius);
        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) return getString(R.string.zodiac_capricorn);
        return getString(R.string.zodiac_unknown);
    }

    private void clearAllFields() {
        birthdat.setText("");
        cyear.setText("0");
        cmonth.setText("0");
        cdays.setText("0");
        cryea.setText("0");
        crmont.setText("0");
        crwee.setText("0");
        crday.setText("0");
        crhour.setText("0");
        crminute.setText("0");
        crsecon.setText("0");
        zsign.setText("0");
        zmonth.setText("0");
        zchinese.setText("0");
    }

    private void showThemeAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Theme Selection")
                .setMessage(getString(R.string.dark_mode_disabled))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }
}
