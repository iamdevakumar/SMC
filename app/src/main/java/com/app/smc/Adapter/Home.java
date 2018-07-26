package com.app.smc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smc.Activity.StaffResult;
import com.app.smc.Activity.StaffSchedule;
import com.app.smc.Activity.StaffUpload;
import com.app.smc.Data.HomeMenu;
import com.app.smc.Helper.Constants;
import com.app.smc.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class Home extends RecyclerView.Adapter<Home.MyViewHolder> {

    private Context mContext;
    private List<HomeMenu> homeList;
    String role;
    public Home(Context mContext, List<HomeMenu> homeList,String role) {
        this.mContext = mContext;
        this.homeList = homeList;
        this.role=role;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Constants constants=new Constants();
        HomeMenu menu = homeList.get(position);

        holder.title.setText(menu.getTitle());
        Glide.with(mContext).load(menu.getImage()).into(holder.thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role.equalsIgnoreCase(constants.staff)) {
                    if (position == 0) {
                        mContext.startActivity(new Intent(mContext, StaffUpload.class));
                    } else if (position == 1) {
                        mContext.startActivity(new Intent(mContext, StaffResult.class));
                    } else if (position == 2) {
                        mContext.startActivity(new Intent(mContext, StaffSchedule.class));
                    }
                }
            else if (role.equalsIgnoreCase(constants.parent)) {
                if (position == 0) {
                    mContext.startActivity(new Intent(mContext, StaffUpload.class));
                } else if (position == 1) {
                    mContext.startActivity(new Intent(mContext, StaffResult.class));
                } else if (position == 2) {
                    mContext.startActivity(new Intent(mContext, StaffSchedule.class));
                }
            }else if (role.equalsIgnoreCase(constants.student)) {
                    if (position == 0) {
                        mContext.startActivity(new Intent(mContext, StaffUpload.class));
                    } else if (position == 1) {
                        mContext.startActivity(new Intent(mContext, StaffResult.class));
                    } else if (position == 2) {
                        mContext.startActivity(new Intent(mContext, StaffSchedule.class));
                    }
                }
        }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView title;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.home_iv);
            title = itemView.findViewById(R.id.home_tv);
            cardView = itemView.findViewById(R.id.home_cv);
        }
    }

}
