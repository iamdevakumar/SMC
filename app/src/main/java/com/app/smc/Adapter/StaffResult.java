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

import com.app.smc.Activity.StaffResultStudent;
import com.app.smc.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class StaffResult extends RecyclerView.Adapter<StaffResult.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String,String>> staffresultList;

    public StaffResult(Context mContext, ArrayList<HashMap<String, String>> staffresultList) {
        this.mContext = mContext;
        this.staffresultList = staffresultList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_result_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final HashMap<String,String> map = staffresultList.get(position);

        holder.no.setText(map.get("ic_no"));
        holder.name.setText(map.get("username"));
        holder.grade.setText(map.get("grade"));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, StaffResultStudent.class);
                intent.putExtra("student_id", map.get("user_id"));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffresultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView no, name, grade;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.staff_result_no);
            name = itemView.findViewById(R.id.staff_result_name);
            grade = itemView.findViewById(R.id.staff_result_grade);
            cardView = itemView.findViewById(R.id.cv_staff_result);
        }
    }
}
