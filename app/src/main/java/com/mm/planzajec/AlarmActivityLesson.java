package com.mm.planzajec;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivityLesson extends AppCompatActivity {

    private TextView textViewHour;
    private TextView textViewLessonName;
    private TextView textViewRoom;
    private TextView textViewSupervisor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_lesson);

        textViewHour = findViewById(R.id.textViewHourSchedule);
        textViewLessonName = findViewById(R.id.textViewLessonName);
        textViewRoom = findViewById(R.id.textViewRoom);
        textViewSupervisor = findViewById(R.id.textViewSupervisor);

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
    }

    public static String getDayName(int day) {
        switch (day) {
            case 1: {
                return "Monday";
            }
            case 2: {
                return "Tuesday";
            }
            case 3: {
                return "Wednesday";
            }
            case 4: {
                return "Thursday";
            }
            case 5: {
                return "Friday";
            }
            case 6: {
                return "Saturday";
            }
            case 7: {
                return "Sunday";
            }
        }
        return "Wrong Day";
    }
}