package com.app.smc.Activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import com.app.smc.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StaffSchedule extends AppCompatActivity {

    private Calendar calendar;
    private TextInputEditText etdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Schedule List");
        setContentView(R.layout.activity_staff_schedule);

        calendar = Calendar.getInstance();
        etdate = findViewById(R.id.staff_schedule_date);

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
    }

    private void updateDate() {

        String format = "dd/MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        etdate.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
