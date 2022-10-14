package edu.wbar.jpcalendar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;


public class DatePickerActivity extends AppCompatActivity {

    private CalendarView datePicker;
    private long initialDate = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        this.setTitle(R.string.activity_date_picker_title);

        datePicker = findViewById(R.id.calendarView);
        long current = getIntent().getLongExtra("dt", 0);
        datePicker.setDate(current);

        initialDate = datePicker.getDate();

        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int apiVer = android.os.Build.VERSION.SDK_INT;
                if (apiVer < Build.VERSION_CODES.LOLLIPOP) {
                    if (datePicker.getDate() == initialDate) {
                        return;
                    }
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("year", year);
                returnIntent.putExtra("month", month);
                returnIntent.putExtra("day", dayOfMonth);

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
