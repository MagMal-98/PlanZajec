package com.mm.planzajec;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanItemOptionsActivity extends AppCompatActivity {

    private Button buttonSupervisorSchedule;
    private Button buttonRoomSchedule;
    private Button buttonCourseSchedule;
    private Button buttonPlanNotification;
    private TextView textViewLessonDate;
    private TextView textViewLessonTime;
    private Button buttonLessonDate;
    private Button buttonLessonTime;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("Theme", Activity.MODE_PRIVATE);
        String color = preferences.getString("themeKey", "");
        if (color.equals("red")) getTheme().applyStyle(R.style.AppThemeRed, true);
        else getTheme().applyStyle(R.style.AppThemeBlue, true);

        setContentView(R.layout.activity_plan_item_options);

        buttonSupervisorSchedule = findViewById(R.id.button_show_supervisor);
        buttonRoomSchedule = findViewById(R.id.button_show_room);
        buttonCourseSchedule = findViewById(R.id.button_show_course);
        buttonPlanNotification = findViewById(R.id.button_set_notification_schedule);

        textViewLessonDate = findViewById(R.id.text_view_lesson_date);
        textViewLessonTime = findViewById(R.id.text_view_lesson_time);
        buttonLessonDate = findViewById(R.id.button_lesson_date);
        buttonLessonTime = findViewById(R.id.button_lesson_time);

        buttonLessonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        buttonLessonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        buttonSupervisorSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String supervisorSchedule = getIntent().getStringExtra("supervisor");
                Intent intent;
                intent = new Intent(PlanItemOptionsActivity.this, MainActivity.class);
                intent.putExtra("supervisor", supervisorSchedule);
                startActivity(intent);
            }

        });

        buttonRoomSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomSchedule = getIntent().getStringExtra("room");
                Intent intent;
                intent = new Intent(PlanItemOptionsActivity.this, MainActivity.class);
                intent.putExtra("room", roomSchedule);
                startActivity(intent);
            }
        });

        buttonCourseSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseSchedule = getIntent().getStringExtra("courseName");
                Intent intent;
                intent = new Intent(PlanItemOptionsActivity.this, MainActivity.class);
                intent.putExtra("course", courseSchedule);
                startActivity(intent);
            }
        });

        buttonPlanNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String supervisorSchedule = getIntent().getStringExtra("supervisor");
                String roomSchedule = getIntent().getStringExtra("room");
                String courseSchedule = getIntent().getStringExtra("courseName");
                String timeSchedule = getIntent().getStringExtra("hour");
                int daySchedule = getIntent().getIntExtra("weekDay", 0);

                String lesson_date = textViewLessonDate.getText().toString();
                String lesson_time = textViewLessonTime.getText().toString();
                if (savedDate == null) {
                    Toast.makeText(getApplicationContext(), "Please insert a date and time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lesson_date.trim().isEmpty() && savedDate != null) {
                    Toast.makeText(getApplicationContext(), "Please insert a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lesson_time.trim().isEmpty() && savedDate != null) {
                    Toast.makeText(getApplicationContext(), "Please insert time", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                Intent intent = new Intent(getApplicationContext(), AlarmReceiverLesson.class);
                intent.putExtra("notificationId", m);
                intent.putExtra("hour", timeSchedule);
                intent.putExtra("courseName", courseSchedule);
                intent.putExtra("room", roomSchedule);
                intent.putExtra("supervisor", supervisorSchedule);
                intent.putExtra("day", daySchedule);

                long timeInMilliseconds = savedDate.getTime();

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), m,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
                finish();
                Toast.makeText(getApplicationContext(), "Notification is set", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Calendar calendar1;
    Date savedDate;

    private void handleDateButton() {
        final Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, date);
                savedDate = calendar1.getTime();

                String dateText = DateFormat.format("d MMM yyyy", calendar1).toString();

                textViewLessonDate.setText(dateText);

            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void handleTimeButton() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int HOUR, MINUTE;
                HOUR = hourOfDay;
                MINUTE = minute;
                String time = HOUR + ":" + MINUTE;

                calendar1.set(Calendar.HOUR_OF_DAY, HOUR);
                calendar1.set(Calendar.MINUTE, MINUTE);
                savedDate = calendar1.getTime();

                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                try {
                    Date date = f24Hours.parse(time);
                    textViewLessonTime.setText(f24Hours.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, 12, 0, true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }
}