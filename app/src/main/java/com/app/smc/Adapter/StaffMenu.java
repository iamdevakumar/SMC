package com.app.smc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.smc.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.HashMap;

public class StaffMenu extends RecyclerView.Adapter<StaffMenu.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String,String>> staffMenuList;

    public StaffMenu(Context mContext, ArrayList<HashMap<String, String>> staffMenuList) {
        this.mContext = mContext;
        this.staffMenuList = staffMenuList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final HashMap<String,String> itemmap = staffMenuList.get(position);

        Glide.with(mContext).load(itemmap.get("thumbnail")).thumbnail(0.1f).into(holder.thumbnail);

        holder.title.setText(itemmap.get("title"));

    }

    @Override
    public int getItemCount() {
        return staffMenuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView title;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.staff_iv);
            title = itemView.findViewById(R.id.staff_tv);
            cardView = itemView.findViewById(R.id.staff_cv);
        }
    }
}
