package com.Age_Calculator.Age_Calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class fbirthdayAdapter extends RecyclerView.Adapter<fbirthdayAdapter.ViewHolder> {
    private Context context;
    private ArrayList<fbirthaygetclass> userList;

    public fbirthdayAdapter(Context context, ArrayList<fbirthaygetclass> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fbirth_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fbirthaygetclass user = userList.get(position);
        if (user != null) {
            holder.age_txt.setText(user.getAge_txt() != null ? user.getAge_txt() : "N/A");
            holder.name.setText(user.getName() != null ? user.getName() : "N/A");
            holder.birthday.setText(user.getBirthday() != null ? user.getBirthday() : "N/A");
            holder.nextBirthday.setText(user.getNext_birthday() != null ? user.getNext_birthday() : "N/A");
            if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
                Picasso.get().load(user.getImageUrl()).into(holder.profileImage);
            } else {
                holder.profileImage.setImageDrawable(null);
            }


            holder.itemView.setOnClickListener(v -> {
                SharedPreferences prefs = context.getSharedPreferences("CardData", Context.MODE_PRIVATE);
                prefs.edit().putString("child_id", user.getC_id()).apply();
                Log.d("FBirthdayAdapter", "Saved child_id: " + user.getC_id()); // Add this log
                context.startActivity(new Intent(context, getdata_real.class));
            });
        }
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, birthday, nextBirthday, age_txt;
        ShapeableImageView profileImage;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            age_txt = itemView.findViewById(R.id.age_text);
            name = itemView.findViewById(R.id.user_name);
            birthday = itemView.findViewById(R.id.user_birthday);
            nextBirthday = itemView.findViewById(R.id.user_next_birthday);
            profileImage = itemView.findViewById(R.id.profile_image);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}