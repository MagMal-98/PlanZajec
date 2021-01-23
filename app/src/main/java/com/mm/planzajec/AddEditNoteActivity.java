package com.mm.planzajec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;


public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.mm.planzajec.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.mm.planzajec.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.mm.planzajec.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.mm.planzajec.EXTRA_DATE";

    private TextInputEditText editNoteTitle;
    private EditText editNoteDescription;
    private TextView dateNoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editNoteTitle = findViewById(R.id.edit_text_title_note);
        editNoteDescription = findViewById(R.id.edit_text_description_note);
        dateNoteTextView = findViewById(R.id.text_view_date_note);

        Toolbar toolbar = findViewById(R.id.toolbar_notes);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(getResources().getString(R.string.note_add_card));
            editNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editNoteDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle(getResources().getString(R.string.note_add_card));
        }

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_view_date_note);
        textViewDate.setText(currentDate);
    }

    private void saveNote() {
        String title = editNoteTitle.getText().toString();
        String description = editNoteDescription.getText().toString();
        String date = dateNoteTextView.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
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
}