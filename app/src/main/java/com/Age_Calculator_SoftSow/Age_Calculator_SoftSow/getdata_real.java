package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.Age_Calculator_SoftSow.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class getdata_real extends AppCompatActivity {
    RecyclerView get_realRecycle;
    ArrayList<get_realClass> list;
    get_realAdapter adapter;
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdata_real);

//        ScrollView scrollView = findViewById(R.id.scrollVie);
//        scrollView.post(() -> scrollView.smoothScrollTo(0, 1500));

        arrow_back = findViewById(R.id.arrow_back);
        get_realRecycle = findViewById(R.id.get_realRecycler);
        get_realRecycle.setHasFixedSize(true);
        get_realRecycle.setNestedScrollingEnabled(false);
        get_realRecycle.setItemViewCacheSize(20);

        list = new ArrayList<>();
        adapter = new get_realAdapter(this, list);
        get_realRecycle.setLayoutManager(new LinearLayoutManager(this));
        get_realRecycle.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("CardData", MODE_PRIVATE);
        String child_id = prefs.getString("child_id", "");
        Log.d("GetDataReal", "Retrieved child_id: " + child_id);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("famousbirthday").child("DetailsScreen").child(child_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    SharedPreferences prefs = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
                    String lang = prefs.getString("My_Lang", "en");

                    try {
                        String name = ds.child("name").child(lang).getValue(String.class);
                        String birthday = ds.child("birthday").child(lang).getValue(String.class);
                        String next_birt = ds.child("next_birt").child(lang).getValue(String.class);
                        String age = ds.child("age").child(lang).getValue(String.class);
                        String zodiac_trait = ds.child("zodiac_trait").child(lang).getValue(String.class);
                        String chinese_trait = ds.child("chinese_trait").child(lang).getValue(String.class);
                        String Wzodiac_sig = ds.child("Wzodiac_sig").child(lang).getValue(String.class);
                        String Czodiac_sig = ds.child("Czodiac_sig").child(lang).getValue(String.class);
                        String img = ds.child("img").getValue(String.class);
                        String img2 = ds.child("img2").getValue(String.class);

                        get_realClass userData = new get_realClass(
                                name, age, birthday, img, zodiac_trait, img2,
                                chinese_trait, next_birt, child_id, Wzodiac_sig, Czodiac_sig
                        );

                        list.add(userData);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.e("FirebaseError", "Data mapping error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseError", "Database error: " + error.getMessage());
            }
        });

        arrow_back.setOnClickListener(v -> {
            startActivity(new Intent(getdata_real.this, about_us.class));
            finish();
        });

    }
}
