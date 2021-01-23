package com.mm.planzajec;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditExamActivity extends AppCompatActivity {

    public static final String EXTRA_EXAM_ID =
            "com.mm.planzajec.EXTRA_EXAM_ID";
    public static final String EXTRA_EXAM_TITLE =
            "com.mm.planzajec.EXTRA_EXAM_TITLE";
    public static final String EXTRA_EXAM_DATE =
            "com.mm.planzajec.EXTRA_EXAM_DATE";
    public static final String EXTRA_EXAM_TIME =
            "com.mm.planzajec.EXTRA_EXAM_TIME";
    public static final String EXTRA_EXAM_TIME_TO_NOTIFY =
            "com.mm.planzajec.EXTRA_EXAM_TIME_TO_NOTIFY";
    public static final String EXTRA_EXAM_NOTIFICATION_ID =
            "com.mm.planzajec.EXTRA_EXAM_NOTIFICATION_ID";

    private TextInputEditText editTextExamTitle;
    private TextView textViewExamDate;
    private TextView textViewExamTime;
    private Button buttonExamDate;
    private Button buttonExamTime;

    private static final String TAG = "ExamDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exam);

        editTextExamTitle = findViewById(R.id.edit_text_exam_title);
        textViewExamDate = findViewById(R.id.text_view_exam_date);
        textViewExamTime = findViewById(R.id.text_view_exam_time);
        buttonExamDate = findViewById(R.id.button_exam_date);
        buttonExamTime = findViewById(R.id.button_exam_time);

        editTextExamTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_exams);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_EXAM_ID)) {
            setTitle(getResources().getString(R.string.exam_edit_card));
            edit_flag = true;
            editTextExamTitle.setText(intent.getStringExtra(EXTRA_EXAM_TITLE));
            textViewExamDate.setText(intent.getStringExtra(EXTRA_EXAM_DATE));
            textViewExamTime.setText(intent.getStringExtra(EXTRA_EXAM_TIME));
            edit_id = intent.getIntExtra(EXTRA_EXAM_ID, -1);
            edit_notification_id = intent.getIntExtra(EXTRA_EXAM_NOTIFICATION_ID, -1);
        } else {
            setTitle(getResources().getString(R.string.exam_add_card));
        }

        buttonExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        buttonExamTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
    }


    private void saveNote() {
        String exam_title = editTextExamTitle.getText().toString();
        String exam_date = textViewExamDate.getText().toString();
        String exam_time = textViewExamTime.getText().toString();

        if (!exam_date.isEmpty() && !exam_time.isEmpty() && savedDate == null) {
            String edit = exam_date + " " + exam_time;
            editDate = stringToDate(edit, "d MMM yyyy HH:mm");
        }
        if (savedDate == null && editDate == null) {
            Toast.makeText(this, "Please insert a date and time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (exam_title.trim().isEmpty() && savedDate == null && editDate == null) {
            Toast.makeText(this, "Please insert a title, date and time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (exam_title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }
        if (exam_date.trim().isEmpty() && savedDate != null) {
            Toast.makeText(this, "Please insert a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (exam_time.trim().isEmpty() && savedDate != null) {
            Toast.makeText(this, "Please insert time", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        data.putExtra(EXTRA_EXAM_TITLE, exam_title);
        data.putExtra(EXTRA_EXAM_DATE, exam_date);
        data.putExtra(EXTRA_EXAM_TIME, exam_time);
        if (editDate == null) {
            data.putExtra(EXTRA_EXAM_TIME_TO_NOTIFY, savedDate.getTime());
            data.putExtra(EXTRA_EXAM_ID, m);
            data.putExtra(EXTRA_EXAM_NOTIFICATION_ID, m);
        } else {
            data.putExtra(EXTRA_EXAM_TIME_TO_NOTIFY, editDate.getTime());
            data.putExtra(EXTRA_EXAM_ID, edit_id);
            data.putExtra(EXTRA_EXAM_NOTIFICATION_ID, edit_notification_id);
        }

        setResult(RESULT_OK, data);

        int a = data.getIntExtra(EXTRA_EXAM_NOTIFICATION_ID, -1);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiverExam.class);
        intent.putExtra("notificationId", a);
        intent.putExtra("message", exam_title);
        intent.putExtra("date", exam_date);
        intent.putExtra("hour", exam_time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), a,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, data.getLongExtra(EXTRA_EXAM_TIME_TO_NOTIFY, 0), pendingIntent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_exam_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note_exam:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Calendar calendar1;
    Calendar calendar2;
    Date savedDate;
    Date editDate;
    int edit_id;
    int edit_notification_id;
    boolean edit_flag = false;

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

                if (calendar2 != null) {
                    calendar1.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
                    calendar1.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
                }

                if (edit_flag) {
                    editDate = calendar1.getTime();
                } else {
                    savedDate = calendar1.getTime();
                }
                String dateText = DateFormat.format("d MMM yyyy", calendar1).toString();

                textViewExamDate.setText(dateText);

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

                if (calendar1 == null) {
                    calendar2 = Calendar.getInstance();
                    calendar2.set(Calendar.YEAR, 2020);
                    calendar2.set(Calendar.MONTH, 10);
                    calendar2.set(Calendar.DAY_OF_MONTH, 30);
                    calendar2.set(Calendar.HOUR_OF_DAY, HOUR);
                    calendar2.set(Calendar.MINUTE, MINUTE);
                } else {
                    calendar1.set(Calendar.HOUR_OF_DAY, HOUR);
                    calendar1.set(Calendar.MINUTE, MINUTE);
                    if (edit_flag) {
                        editDate = calendar1.getTime();
                    } else {
                        savedDate = calendar1.getTime();
                    }
                }

                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                try {
                    Date date = f24Hours.parse(time);
                    textViewExamTime.setText(f24Hours.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, 12, 0, true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }

    private Date stringToDate(String aDate, String aFormat) {
        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}