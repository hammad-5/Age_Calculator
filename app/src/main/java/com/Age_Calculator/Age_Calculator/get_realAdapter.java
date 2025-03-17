package com.Age_Calculator.Age_Calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class get_realAdapter extends RecyclerView.Adapter<get_realAdapter.ViewHolder> {

    private Context context;
    private ArrayList<get_realClass> list;

    public get_realAdapter(Context context, ArrayList<get_realClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_realdesign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        get_realClass user = list.get(position);
        if (user != null) {
            holder.name.setText(user.getName() );
            holder.age.setText(user.getAge() );
            holder.birthday.setText(user.getBirthday() );
            holder.zodiac_trait.setText(user.getZodiac_trait() );
            holder.chinese_trait.setText(user.getChinese_trait() );
            holder.next_birt.setText(user.getNext_birt() );
            holder.Wzodiac_sig.setText(user.getWzodiac_sig());
            holder.Czodiac_sig.setText(user.getCzodiac_sig());
            Picasso.get().load(user.getImg()).into(holder.img);
            Picasso.get().load(user.getImg2()).into(holder.img2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, birthday, zodiac_trait, chinese_trait, next_birt,Wzodiac_sig,Czodiac_sig;
        ImageView img, img2;
        CardView get_readCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            birthday = itemView.findViewById(R.id.birthday);
            img = itemView.findViewById(R.id.zodiac_icon);
            zodiac_trait = itemView.findViewById(R.id.zodiac_traits);
            img2 = itemView.findViewById(R.id.chinese_zodiac_icon);
            chinese_trait = itemView.findViewById(R.id.chinese_traits);
            next_birt = itemView.findViewById(R.id.next_birth);
            get_readCard = itemView.findViewById(R.id.get_realCard);
            Wzodiac_sig= itemView.findViewById(R.id.Wzodiac_sign);
            Czodiac_sig= itemView.findViewById(R.id.Czodiac_sign);
        }
    }
}