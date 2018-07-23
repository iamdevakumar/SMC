package com.app.smc.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.app.smc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private BottomNavigationView bottomNavigationView;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Staff");
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Staff());
        return view;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_staff:
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Staff");
                    fragment = new Staff();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_student:
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Student");
                    fragment = new Student();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_parent:
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Parent");
                    fragment = new Parent();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
