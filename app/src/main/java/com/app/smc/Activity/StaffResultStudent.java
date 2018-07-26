package com.app.smc.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.smc.Helper.Constants;
import com.app.smc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffResultStudent extends AppCompatActivity {

    private TextView tvname, tvno, tvemail, tvmobile, tvsubject, tvmark, tvpoint, tvgrade, tvqualification, tvaddress1, tvaddress2;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Student Result");
        setContentView(R.layout.activity_staff_result_student);

        tvname = findViewById(R.id.staff_result_student_name);
        tvno = findViewById(R.id.staff_result_student_no);
        tvemail = findViewById(R.id.staff_result_student_email);
        tvmobile = findViewById(R.id.staff_result_student_mobile);
        tvsubject = findViewById(R.id.staff_result_student_subject);
        tvmark = findViewById(R.id.staff_result_student_mark);
        tvpoint = findViewById(R.id.staff_result_student_point);
        tvgrade = findViewById(R.id.staff_result_student_grade);
        tvqualification = findViewById(R.id.staff_result_student_qualification);
        tvaddress1 = findViewById(R.id.staff_result_student_address1);
        tvaddress2 = findViewById(R.id.staff_result_student_address2);
        profileImage = findViewById(R.id.staff_result_student_iv);

        Intent intent = getIntent();
        String id = intent.getStringExtra("student_id");
        new fetchStudentResult(this, id).execute();


    }

    private class fetchStudentResult extends AsyncTask<String, Integer, String>{

        Context context;
        String url = Constants.BASE_URL + Constants.STAFF_RESULT_STUDENT;
        ProgressDialog progress;
        String id,no,name,grade,email,mobile,subject,mark,point,qualification,address1,address2;

        public fetchStudentResult(Context context, String id) {
            this.context = context;
            this.id = id;
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
                    .add("student_id", id)
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

                        no=jcat.getString("ic_no");
                        name=jcat.getString("username");
                        grade=jcat.getString("grade");
                        email=jcat.getString("email");
                        mobile=jcat.getString("mobile");
                        subject=jcat.getString("subject");
                        mark=jcat.getString("mark");
                        point=jcat.getString("point");
                        qualification=jcat.getString("qualification");
                        address1=jcat.getString("t_address");
                        address2=jcat.getString("p_address");

                        tvno.setText(no);
                        tvname.setText(name);
                        tvgrade.setText(grade);
                        tvemail.setText(email);
                        tvmobile.setText(mobile);
                        tvsubject.setText(subject);
                        tvmark.setText(mark);
                        tvpoint.setText(point);
                        tvqualification.setText(qualification);
                        tvaddress1.setText(address1);
                        tvaddress2.setText(address2);

                    }

                } else
                    Toast.makeText(context, jonj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
