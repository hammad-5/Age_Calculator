package com.Age_Calculator.Age_Calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Age_Calculator.R;
import com.example.hijrimonth_find.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class dashboard extends AppCompatActivity {

    EditText birthdat, currentdat;
    TextView cyear, cmonth, cdays;
    TextView cryea, crmont, crwee, crday, crhour, crminute, crsecon;
    Button calculateButton;
    Calendar birthCalendar, currentCalendar;
    TextView zsign, zmonth, zchinese;

    private DrawerLayout drawerLayout;
    NavigationView navigationview,bottom_navi;
    ImageView menuIco;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // **Initialize UI components**
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

        zsign = findViewById(R.id.zsign);
        zmonth = findViewById(R.id.zmonth);
        zchinese = findViewById(R.id.zchinese);

        drawerLayout =findViewById(R.id.drawer_layout);
        navigationview=findViewById(R.id.navig_bar);

        menuIco=findViewById(R.id.menuIcon);



        // Menu icon click to open drawer
        menuIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationview); // Open the drawer explicitly
            }
        });

        // NavigationView menu item click listener
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.feed) { // Match the ID from nav.xml
                    Intent intent = new Intent(dashboard.this, Feedback.class);
                    startActivity(intent);
                    finish(); // Optional: finish current activity
                    drawerLayout.closeDrawers(); // Close drawer after selection
                    return true;
                }
                return false;
            }
        });
        // Bottom Navigation Setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);


        // **Make EditTexts uneditable**
        birthdat.setFocusable(false);
        birthdat.setClickable(false);

        currentdat.setFocusable(false);
        currentdat.setClickable(false);

        // **Initialize Calendars**
        birthCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();

        // **Set Current Date in `currentdat` EditText**
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);
        currentdat.setText(day + " - " + (month + 1) + " - " + year);

        // **Set onClick Listeners for EditTexts**
        birthdat.setOnClickListener(v -> showDatePickerDialog(birthdat));
        currentdat.setOnClickListener(v -> showDatePickerDialog(currentdat));

        // **Button Click: Validate and Calculate Age**
        calculateButton.setOnClickListener(v -> {
            // Check if both EditTexts are filled
            if (birthdat.getText().toString().isEmpty() || currentdat.getText().toString().isEmpty()) {
                // Show error message
                new AlertDialog.Builder(dashboard.this)
                        .setTitle("Error")
                        .setMessage("Please fill in Birth Date")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                // Proceed with calculation
                int birthYear = birthCalendar.get(Calendar.YEAR);
                int birthMonth = birthCalendar.get(Calendar.MONTH);
                int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);
                calculateAge(birthYear, birthMonth, birthDay);
            }
        });

        // **Detect Right-side Icon Click on EditTexts**
        birthdat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (birthdat.getRight() - birthdat.getCompoundDrawables()[2].getBounds().width())) {
                    showDatePickerDialog(birthdat);
                    return true;
                }
            }
            return false;
        });

        currentdat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (currentdat.getRight() - currentdat.getCompoundDrawables()[2].getBounds().width())) {
                    showDatePickerDialog(currentdat);
                    return true;
                }
            }
            return false;
        });

    }

    // Handle Bottom Navigation Clicks
    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            return true;
        } else if (itemId == R.id.about_ic) {
            startActivity(new Intent(dashboard.this, about_us.class));
           // showProgressDialog();  // Show progress when About Us is clicked

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

//    private void showProgressDialog() {
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false); // Prevent dismissing by back button
//        progressDialog.show();
//
//        // Dismiss the dialog after 2 seconds (2000ms)
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        }, 2000);
//    }
//     **Show Date Picker Dialog**
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

    // **Calculate Age**
    private void calculateAge(int birthYear, int birthMonth, int birthDay) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(birthYear, birthMonth, birthDay, 0, 0, 0);

        long differenceInMilliseconds = today.getTimeInMillis() - birthDate.getTimeInMillis();
        long totalSeconds = differenceInMilliseconds / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;
        long totalDays = totalHours / 24;
        long totalWeeks = totalDays / 7;

        int years = today.get(Calendar.YEAR) - birthYear;
        int months = today.get(Calendar.MONTH) - birthMonth;
        int days = today.get(Calendar.DAY_OF_MONTH) - birthDay;
        if (days < 0) {
            months--;
            days += 30;
        }
        if (months < 0) {
            years--;
            months += 12;
        }
        int totalMonths = (years * 12) + months;

        cyear.setText(String.valueOf(years));
        cmonth.setText(String.valueOf(months));
        cdays.setText(String.valueOf(days));

        cryea.setText(String.valueOf(years));
        crmont.setText(String.valueOf(totalMonths));
        crwee.setText(String.valueOf(totalWeeks));
        crday.setText(String.valueOf(totalDays));
        crhour.setText(String.valueOf(totalHours));
        crminute.setText(String.valueOf(totalMinutes));
        crsecon.setText(String.valueOf(totalSeconds));

        String zodiacSign = getZodiacSign(birthMonth + 1, birthDay);
        String monthName = getMonthName(birthMonth + 1); // Convert month number to name
        // Add these lines at the end of the method
        String chineseZodiac = getChineseZodiac(birthYear);
        zchinese.setText(chineseZodiac);
        zsign.setText(zodiacSign);
        zmonth.setText(monthName); // Show month name instead of number
    }

    // **Method to get month name from number**
    private String getMonthName(int month) {
        String[] months = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        if (month >= 1 && month <= 12) {
            return months[month - 1];
        }
        return "Unknown";
    }
    // Chinese Zodiac calculation method
    private String getChineseZodiac(int year) {
        String[] zodiac = {
                "Rat 🐀", "Ox 🐂", "Tiger 🐅", "Rabbit 🐇", "Dragon 🐉", "Snake 🐍",
                "Horse 🐎", "Goat 🐐", "Monkey 🐒", "Rooster 🐓", "Dog 🐕", "Pig 🐖"
        };

        int baseYear = 1900; // Rat year
        int remainder = (year - baseYear) % 12;
        if (remainder < 0) remainder += 12; // Handle negative years

        return zodiac[remainder];
    }

    private String getZodiacSign(int month, int day) {
        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) return "Aquarius ♒";
        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) return "Pisces ♓";
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) return "Aries ♈";
        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) return "Taurus ♉";
        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) return "Gemini ♊";
        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) return "Cancer ♋";
        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) return "Leo ♌";
        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) return "Virgo ♍";
        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) return "Libra ♎";
        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) return "Scorpio ♏";
        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) return "Sagittarius ♐";
        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) return "Capricorn ♑";

        return "Unknown";
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Closes all activities in the task and exits the app
    }

}
