package com.app.smc.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.smc.R;

public class Login extends AppCompatActivity {

    private TextInputEditText etuname, etpass;
    private Button btnlogin;
    private TextView tvforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        etuname = findViewById(R.id.log_et_uname);
        etpass = findViewById(R.id.log_et_pass);
        tvforgot = findViewById(R.id.log_tv_forgot);
        btnlogin = findViewById(R.id.log_btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(Login.this, Home.class));
            }
        });
    }
}
