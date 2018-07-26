package com.app.smc.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.app.smc.Helper.Constants;
import com.app.smc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffSchedule extends AppCompatActivity {

    private Calendar calendar;
    private TextInputEditText etdate, etsubject, etcourse, etdesc;
    private Button btnschedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Schedule List");
        setContentView(R.layout.activity_staff_schedule);

        calendar = Calendar.getInstance();
        etdate = findViewById(R.id.staff_schedule_date);
        etsubject = findViewById(R.id.staff_schedule_subject);
        etcourse = findViewById(R.id.staff_schedule_course);
        etdesc = findViewById(R.id.staff_schedule_desc);
        btnschedule = findViewById(R.id.staff_schedule_btn);

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();

            }
        };
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(StaffSchedule.this, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        btnschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = etdate.getText().toString().trim();
                String subject = etsubject.getText().toString().trim();
                String course = etcourse.getText().toString().trim();
                String description = etdesc.getText().toString().trim();
                new scheduleList(StaffSchedule.this, date, course, subject,  description).execute();
            }
        });
    }

    private void updateDate() {

        String format = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        etdate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private class scheduleList extends AsyncTask<String, Integer, String>{

        private Context context;
        private ProgressDialog progress;
        private String date, subject, course, description;
        String url = Constants.BASE_URL + Constants.STAFF_ADD_SCHEDULE;

        public scheduleList(Context context, String date, String course, String subject, String description) {
            this.context = context;
            this.date = date;
            this.subject = subject;
            this.course = course;
            this.description = description;
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
                    .add("date", date)
                    .add("class", course)
                    .add("subject", subject)
                    .add("description", description)
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
                    String data = jonj.getString("message");

                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, jonj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
