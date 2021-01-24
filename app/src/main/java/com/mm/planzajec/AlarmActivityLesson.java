package com.mm.planzajec;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivityLesson extends AppCompatActivity {

    private TextView textViewHour;
    private TextView textViewLessonName;
    private TextView textViewRoom;
    private TextView textViewSupervisor;
    private Button buttonClose;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_lesson);

        textViewHour = findViewById(R.id.textViewHourSchedule);
        textViewLessonName = findViewById(R.id.textViewLessonName);
        textViewRoom = findViewById(R.id.textViewRoom);
        textViewSupervisor = findViewById(R.id.textViewSupervisor);
        buttonClose = findViewById(R.id.button_lesson_close);

        Bundle extras = getIntent().getExtras();
        String hour = extras.getString("hour");
        String name = extras.getString("name");
        String room = extras.getString("room");
        String supervisor = extras.getString("supervisor");
        int day = extras.getInt("day", 0);

        textViewHour.setText(getDayName(day) + ", " + hour);
        textViewLessonName.setText(name);
        textViewRoom.setText(room);
        textViewSupervisor.setText(supervisor);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public String getDayName(int day) {
        switch (day) {
            case 1: {
                return getResources().getString(R.string.monday);
            }
            case 2: {
                return getResources().getString(R.string.tuesday);
            }
            case 3: {
                return getResources().getString(R.string.wednesday);
            }
            case 4: {
                return getResources().getString(R.string.thursday);
            }
            case 5: {
                return getResources().getString(R.string.friday);
            }
        }
        return "Wrong Day";
    }
}