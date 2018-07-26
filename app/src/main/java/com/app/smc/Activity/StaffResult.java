package com.app.smc.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app.smc.Helper.Constants;
import com.app.smc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffResult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.app.smc.Adapter.StaffResult adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<HashMap<String,String>> staffresultList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("All Students Results");
        setContentView(R.layout.activity_staff_result);

        new fetchStaffResult(this).execute();

        recyclerView = findViewById(R.id.rv_staff_result);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    private class fetchStaffResult extends AsyncTask<String, Integer, String>{

        Context context;
        String url = Constants.BASE_URL + Constants.STAFF_RESULT;
        ProgressDialog progress;
        HashMap<String,String> map;
        String no,name,grade,id;

        public fetchStaffResult(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(context);
            progress.setMessage("Please wait ....");
            progress.setTitle("Loading");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String jsonData = null;
            Response response = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = client.newCall(request);

            try {
                response = call.execute();

                if (response.isSuccessful()) {
                    jsonData = response.body().string();
                } else {
                    jsonData = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);

            progress.dismiss();
            Log.v("result", "" + jsonData);
            JSONObject jonj = null;
            try {
                jonj = new JSONObject(jsonData);
                if (jonj.getString("success").equalsIgnoreCase(
                        "1")) {

                    String data = jonj.getString("marks");
                    JSONArray array = new JSONArray(data);
                    for(int i=0;i<array.length();i++) {
                        JSONObject jcat = array.getJSONObject(i);
                        map=new HashMap<String, String>();

                        id=jcat.getString("user_id");
                        no=jcat.getString("ic_no");
                        name=jcat.getString("username");
                        grade=jcat.getString("grade");

                        map.put("ic_no",no);
                        map.put("username",name);
                        map.put("grade",grade);
                        map.put("user_id", id);

                        staffresultList.add(map);
                    }

                    adapter = new com.app.smc.Adapter.StaffResult(StaffResult.this,staffresultList);
                    recyclerView.setAdapter(adapter);

                } else
                    Toast.makeText(context, jonj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
