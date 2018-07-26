package com.app.smc.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.smc.Data.HomeMenu;
import com.app.smc.Helper.Constants;
import com.app.smc.Helper.GetSet;
import com.app.smc.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private RecyclerView recyclerView;
    private com.app.smc.Adapter.Home adapter;
    private List<HomeMenu> homeList;
    private RecyclerView.LayoutManager mLayoutManager;
    private String role;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Constants.pref = getActivity().getSharedPreferences("SMC",Context.MODE_PRIVATE);
        Constants.editor = Constants.pref.edit();
        role = Constants.pref.getString("role", "");
        homeList = new ArrayList<>();
        adapter = new com.app.smc.Adapter.Home(getActivity(), homeList,role);
        recyclerView = view.findViewById(R.id.rv_home);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        fetchData();
        return view;
    }

    private void fetchData() {

        if (role.equalsIgnoreCase("parent")){

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("PARENT");

            int [] icons = new int[]{

                    R.drawable.parent_result,
                    R.drawable.parent_schedule,
                    R.drawable.parent_performance,
                    R.drawable.parent_queries

            };
            HomeMenu menu = new HomeMenu("Exam Results", icons[0]);
            homeList.add(menu);
            menu = new HomeMenu("Scheduled Class", icons[1]);
            homeList.add(menu);
            menu = new HomeMenu("Student Performance", icons[2]);
            homeList.add(menu);
            menu = new HomeMenu("Report Queries", icons[3]);
            homeList.add(menu);

            adapter.notifyDataSetChanged();

        }
        else if (role.equalsIgnoreCase("student")){

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("STUDENT");

            int [] icons = new int[]{

                    R.drawable.student_livestream,
                    R.drawable.student_notes,
                    R.drawable.student_result

            };
            HomeMenu menu = new HomeMenu("Live Class", icons[0]);
            homeList.add(menu);
            menu = new HomeMenu("Notes", icons[1]);
            homeList.add(menu);
            menu = new HomeMenu("View My Result", icons[2]);
            homeList.add(menu);

            adapter.notifyDataSetChanged();

        }
        else if (role.equalsIgnoreCase("staff")){

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("STAFF");

            int [] icons = new int[]{

                    R.drawable.staff_upload,
                    R.drawable.staff_result,
                    R.drawable.staff_schedule

            };
            HomeMenu menu = new HomeMenu("Upload Notes", icons[0]);
            homeList.add(menu);
            menu = new HomeMenu("Get My Students Results", icons[1]);
            homeList.add(menu);
            menu = new HomeMenu("Schedule List", icons[2]);
            homeList.add(menu);

            adapter.notifyDataSetChanged();

        }
    }

}
