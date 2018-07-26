package com.app.smc.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.app.smc.Helper.Constants;
import com.app.smc.Helper.GetSet;
import com.app.smc.R;

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

public class Login extends AppCompatActivity {

    private TextInputEditText etuname, etpass;
    private Button btnlogin;
    private TextView tvforgot;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Constants.pref = getSharedPreferences("SMC",Context.MODE_PRIVATE);
        Constants.editor = Constants.pref.edit();

        etuname = findViewById(R.id.log_et_uname);
        etpass = findViewById(R.id.log_et_pass);
        tvforgot = findViewById(R.id.log_tv_forgot);
        btnlogin = findViewById(R.id.log_btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = etuname.getText().toString().trim();
                String pass = etpass.getText().toString().trim();
                new login(Login.this, user, pass).execute();
            }
        });
    }

    private class login extends AsyncTask<String, Integer, String>{

        Context context;
        String url = Constants.BASE_URL + Constants.LOGIN;
        ProgressDialog progress;
        String username, password;

        public login(Context context, String username, String password) {
            this.context = context;
            this.username = username;
            this.password = password;
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
                    .add("username", username)
                    .add("password", password)
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

                    GetSet.setId(jonj.getString("userid"));
                    GetSet.setName(jonj.getString("aname"));
                    GetSet.setMobile(jonj.getString("mobile"));
                    GetSet.setRole(jonj.getString("role"));

                    Constants.editor.putString("id", GetSet.getId());
                    Constants.editor.putString("name", GetSet.getName());
                    Constants.editor.putString("mobile", GetSet.getMobile());
                    Constants.editor.putString("role", GetSet.getRole());
                    Constants.editor.commit();

                    startActivity(new Intent(Login.this, Home.class));
                    finish();

                }else if (jonj.getString("success").equalsIgnoreCase(
                        "0")){

                    Toast.makeText(context,jonj.getString("message"),Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
