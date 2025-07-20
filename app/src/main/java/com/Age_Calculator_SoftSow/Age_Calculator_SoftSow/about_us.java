package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Age_Calculator_SoftSow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class about_us extends AppCompatActivity {

    RecyclerView recyclerViesl;
    DatabaseReference databaseReferencebirth;
    public fbirthdayAdapter fbirthdayAdapte;
    ImageView arrow_bac;
    ArrayList<fbirthaygetclass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        showSplash(); // Splash overlay

        recyclerViesl = findViewById(R.id.recbirthget);
        databaseReferencebirth = FirebaseDatabase.getInstance().getReference("famousbirthday");

        recyclerViesl.setHasFixedSize(true);
        recyclerViesl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        fbirthdayAdapte = new fbirthdayAdapter(this, list);
        recyclerViesl.setAdapter(fbirthdayAdapte);

        // Call Firebase Database
        String lang = getSharedPreferences("SettingsPrefs", MODE_PRIVATE).getString("My_Lang", "en");


        SharedPreferences sharedPreferenc = getSharedPreferences("Card", Context.MODE_PRIVATE);
        String sharedString = sharedPreferenc.getString("sharedStringId", "");
        Log.d("ID", sharedString);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);

        arrow_bac = findViewById(R.id.arrow_back);
        arrow_bac.setOnClickListener(v -> startActivity(new Intent(about_us.this, dashboard.class)));

        databaseReferencebirth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.getKey();

                    String id = ds.child("id").getValue(String.class);
                    String birthday = ds.child("birthday").child(lang).getValue(String.class);
                    String next_birthday = ds.child("next_birthday").child(lang).getValue(String.class);
                    String imageUrl = ds.child("imageUrl").getValue(String.class);
                    String name = ds.child("name").child(lang).getValue(String.class);
                    String age_txt = ds.child("age_txt").child(lang).getValue(String.class);

                    // ✅ Skip completely empty or malformed items
                    if ((id == null || id.trim().isEmpty()) &&
                            (name == null || name.trim().isEmpty()) &&
                            (birthday == null || birthday.trim().isEmpty())) {
                        Log.w("Firebase", "Skipping completely empty or null item: " + key);
                        continue;
                    }

                    fbirthaygetclass person = new fbirthaygetclass();
                    person.setKey(key);
                    person.setId(id);
                    person.setBirthday(birthday);
                    person.setNext_birthday(next_birthday);
                    person.setImageUrl(imageUrl);
                    person.setName(name);
                    person.setAge_txt(age_txt);

                    list.add(person);
                }

                fbirthdayAdapte.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(about_us.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            startActivity(new Intent(about_us.this, dashboard.class));
            return true;
        } else if (itemId == R.id.about_ic) {
            showSplash();
            return true;
        } else if (itemId == R.id.privacy) {
            startActivity(new Intent(about_us.this, privacy_policy.class));
            return true;
        } else if (itemId == R.id.setting_ic) {
            startActivity(new Intent(about_us.this, setting.class));
            return true;
        }
        return false;
    }

    private void showSplash() {
        final Dialog splashDialog = new Dialog(this);
        splashDialog.requestWindowFeature(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        splashDialog.setContentView(R.layout.activity_progress_overlay);
        splashDialog.setCancelable(false);

        if (splashDialog.getWindow() != null) {
            splashDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            splashDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            splashDialog.getWindow().setGravity(Gravity.CENTER);
        }

        splashDialog.show();

        new Handler().postDelayed(splashDialog::dismiss, 2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
