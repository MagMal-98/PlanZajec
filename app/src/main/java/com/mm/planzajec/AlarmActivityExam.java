package com.mm.planzajec;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivityExam extends AppCompatActivity {

    private TextView textViewDate;
    private TextView textViewHour;
    private TextView textViewMessage;
    private Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_exam);

        textViewHour = findViewById(R.id.textView_hour);
        textViewDate = findViewById(R.id.textView_date);
        textViewMessage = findViewById(R.id.textView_message);
        buttonClose = findViewById(R.id.button_exam_close);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("message");
        String time = extras.getString("hour");
        String day = extras.getString("date");

        textViewDate.setText(day);
        textViewHour.setText(time);
        textViewMessage.setText(msg);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
