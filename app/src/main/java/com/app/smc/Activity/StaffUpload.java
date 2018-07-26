package com.app.smc.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.smc.Helper.Constants;
import com.app.smc.R;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffUpload extends AppCompatActivity{

    private NiceSpinner spclass, spsubject, spchoose;
    private TextView tvfile;
    private Button btnupload;
    private ImageView attach;
    private TextInputEditText etchapter;
    private ArrayList<String> standard, subject;
    private static final int PICKFILE = 123;
    private ProgressDialog dialog;
    private String filePath;
    List<String> choose = new LinkedList<>(Arrays.asList("Notes", "Dictionary"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Upload Notes");
        setContentView(R.layout.activity_staff_upload);

        spclass = findViewById(R.id.staff_upload_class);
        spsubject = findViewById(R.id.staff_upload_subject);
        spchoose = findViewById(R.id.staff_upload_choose);
        etchapter = findViewById(R.id.staff_upload_chapter);
        tvfile = findViewById(R.id.staff_upload_file);
        btnupload = findViewById(R.id.staff_upload_btn);
        attach = findViewById(R.id.staff_upload_iv);
        standard = new ArrayList<>();
        subject = new ArrayList<>();

        spchoose.attachDataSource(choose);
        new fetchClass(this).execute();
        new fetchSubject(this).execute();

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,PICKFILE);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etchapter.getText().toString().isEmpty()){
                    etchapter.setError("Chapter feild is Empty!");
                }else if (tvfile.getText().toString().isEmpty()){
                    tvfile.setError("Please attach File!");
                }else {
                    dialog = new ProgressDialog(StaffUpload.this);
                    dialog.setMessage("Please wait ....");
                    dialog.setTitle("Uploading");
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {

                                  tvfile.setText("Uploading...");
                              }
                          });

                            uploadFile(filePath);
                        }
                    }).start();

                }
            }
        });

    }

    private void uploadFile(final String filePath) {



    }

    private class fetchClass extends AsyncTask<String, Integer, String> {

        Context context;
        String url = Constants.BASE_URL + Constants.CLASS;
        ProgressDialog progress;
        String std;

        public fetchClass(Context context) {
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
                    String data = jonj.getString("classes");
                    JSONArray array = new JSONArray(data);
                    for(int i=0;i<array.length();i++) {
                        JSONObject jcat = array.getJSONObject(i);

                        std=jcat.getString("std");
                        standard.add(std);
                    }
                    spclass.attachDataSource(standard);
                } else
                    Toast.makeText(context, jonj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class fetchSubject extends AsyncTask<String, Integer, String>{

        Context context;
        String url = Constants.BASE_URL + Constants.SUBJECT;
        ProgressDialog progress;
        String sub;


        public fetchSubject(Context context) {
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
                    String data = jonj.getString("subject");
                    JSONArray array = new JSONArray(data);
                    for(int i=0;i<array.length();i++) {
                        JSONObject jcat = array.getJSONObject(i);

                        sub=jcat.getString("subjectname");
                        subject.add(sub);
                    }
                    spsubject.attachDataSource(subject);
                } else
                    Toast.makeText(context, jonj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICKFILE:
                if (resultCode == RESULT_OK) {
                    filePath = data.getData().getPath();
                    }
                break;
        }
    }
}
