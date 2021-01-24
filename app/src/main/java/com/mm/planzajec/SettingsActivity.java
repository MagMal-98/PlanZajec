package com.mm.planzajec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView changeLanguage;
    //private TextView changeColor;
    boolean langSelected = true;
    //boolean colorSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("Theme", Activity.MODE_PRIVATE);
        String color = preferences.getString("themeKey", "");
        if (color.equals("red")) getTheme().applyStyle(R.style.AppThemeRed, true);
        else getTheme().applyStyle(R.style.AppThemeBlue, true);

        setContentView(R.layout.activity_settings);
        setTitle(getResources().getString(R.string.settings_card));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        changeLanguage = findViewById(R.id.text_view_change_language_button);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] languageList = {"English", "Polski"};

                int checkedItem;
                if (langSelected){
                    checkedItem = 0;
                } else checkedItem = 1;

                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Select a language")
                        .setSingleChoiceItems(languageList, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (languageList[which].equals("English")){
                                    setLocale("en");
                                }
                                if (languageList[which].equals("Polski")){
                                    setLocale("pl");
                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }


        });

//        changeColor = findViewById(R.id.text_view_change_color_button);
//        changeColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String[] colorList = {getResources().getString(R.string.blue_theme), getResources().getString(R.string.red_theme)};
//
//                int checkedItem;
//                if (colorSelected){
//                    checkedItem = 0;
//                } else checkedItem = 1;
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
//                builder.setTitle("Select theme")
//                        .setSingleChoiceItems(colorList, checkedItem, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (colorList[which].equals(getResources().getString(R.string.blue_theme))){
//                                    SharedPreferences.Editor editor = getSharedPreferences("Theme", MODE_PRIVATE).edit();
//                                    editor.putString("themeKey", "blue");
//                                    editor.apply();
//                                }
//                                if (colorList[which].equals(getResources().getString(R.string.red_theme))){
//                                    SharedPreferences.Editor editor = getSharedPreferences("Theme", MODE_PRIVATE).edit();
//                                    editor.putString("themeKey", "red");
//                                    editor.apply();
//                                }
//                            }
//                        })
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                recreate();
//                                dialog.dismiss();
//                            }
//                        });
//                builder.create().show();
//            }
//
//
//        });

    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_schedule:
                intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_change_schedule:
                intent = new Intent(SettingsActivity.this, ChangeScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_exams:
                intent = new Intent(SettingsActivity.this, ExamsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_notes:
                intent = new Intent(SettingsActivity.this, NotesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_personalize:
                onBackPressed();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}