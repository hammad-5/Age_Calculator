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

        ScrollView scrollView = findViewById(R.id.scrollVie);
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        arrow_back = findViewById(R.id.arrow_back);
        get_realRecycle = findViewById(R.id.get_realRecycler);

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
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GetDataReal", "Data exists: " + dataSnapshot.exists());
                if (dataSnapshot.exists()) {
                    get_realClass userData = dataSnapshot.getValue(get_realClass.class);
                    if (userData != null) {
                        list.add(userData);  // Add data to the list
                        adapter.notifyDataSetChanged();  // Notify adapter
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GetDataReal", "Error: " + databaseError.getMessage());
            }

        });
        arrow_back.setOnClickListener(v -> {
            startActivity(new Intent(getdata_real.this, about_us.class));
            finish();
        });

    }
}
