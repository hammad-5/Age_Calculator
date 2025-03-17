package com.Age_Calculator.Age_Calculator;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Age_Calculator.R;
import com.example.hijrimonth_find.R;
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

        // Show splash overlay when activity launches.
        showSplash();

        recyclerViesl = findViewById(R.id.recbirthget);
        databaseReferencebirth = FirebaseDatabase.getInstance().getReference("famousbirthday");

        recyclerViesl.setHasFixedSize(true);
        recyclerViesl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        fbirthdayAdapte = new fbirthdayAdapter(this, list);
        recyclerViesl.setAdapter(fbirthdayAdapte);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNavigation);

        arrow_bac = findViewById(R.id.arrow_back);
        arrow_bac.setOnClickListener(v -> startActivity(new Intent(about_us.this, dashboard.class)));

        databaseReferencebirth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Log.d("AboutUs", "Total children: " + snapshot.getChildrenCount());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    fbirthaygetclass productone = new fbirthaygetclass();
                    String firebaseKey = dataSnapshot.getKey();
                    productone.setKey(firebaseKey);

                    if (dataSnapshot.child("name").exists())
                        productone.setName(dataSnapshot.child("name").getValue(String.class));
                    if (dataSnapshot.child("birthday").exists())
                        productone.setBirthday(dataSnapshot.child("birthday").getValue(String.class));
                    if (dataSnapshot.child("next_birthday").exists())
                        productone.setNext_birthday(dataSnapshot.child("next_birthday").getValue(String.class));
                    if (dataSnapshot.child("imageUrl").exists())
                        productone.setImageUrl(dataSnapshot.child("imageUrl").getValue(String.class));
                    if (dataSnapshot.child("age_txt").exists())
                        productone.setAge_txt(dataSnapshot.child("age_txt").getValue(String.class));

                    if (dataSnapshot.child("c_id").exists()) {
                        productone.setC_id(dataSnapshot.child("c_id").getValue(String.class));
                    } else {
                        productone.setC_id(firebaseKey);
                    }

                    if (productone.getName() != null) {
                        list.add(productone);
                        Log.d("AboutUs", "Added item: key=" + firebaseKey +
                                ", c_id=" + productone.getC_id() +
                                ", name=" + productone.getName());
                    }
                }
                fbirthdayAdapte.notifyDataSetChanged();
                Log.d("AboutUs", "Final list size: " + list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(about_us.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleBottomNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            startActivity(new Intent(about_us.this, dashboard.class));
            return true;
        } else if (itemId == R.id.about_ic) {
            // Show splash overlay when "About Us" is clicked.
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashDialog.dismiss();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
