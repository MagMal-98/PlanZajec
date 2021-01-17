package com.mm.planzajec;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivityExam extends AppCompatActivity {

    private TextView textViewDate;
    private TextView textViewHour;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_exam);

        textViewHour = findViewById(R.id.textView_hour);
        textViewDate = findViewById(R.id.textView_date);
        textViewMessage = findViewById(R.id.textView_message);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("message");
        String time = extras.getString("hour");
        String day = extras.getString("date");

        textViewDate.setText(day);
        textViewHour.setText(time);
        textViewMessage.setText(msg);
    }
}
