package com.mm.planzajec;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ReadJson readJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.main_card));

        //start another activity only once after install
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(MainActivity.this, FirstStartActivity.class));
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();
        }

        //navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //tablayout
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        readJson = new ReadJson(this);
        ArrayList<String> weekDays = new ArrayList<>();
        weekDays.add(getResources().getString(R.string.monday));
        weekDays.add(getResources().getString(R.string.tuesday));
        weekDays.add(getResources().getString(R.string.wednesday));
        weekDays.add(getResources().getString(R.string.thursday));
        weekDays.add(getResources().getString(R.string.friday));
        prepareViewPager(viewPager, weekDays);
        tabLayout.setupWithViewPager(viewPager);

        createChannel();
    }

    public ReadJson getReadJson() {
        return readJson;
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> weekDays) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String supervisor = getIntent().getStringExtra("supervisor");
        String room = getIntent().getStringExtra("room");
        String course = getIntent().getStringExtra("course");

        for (int i = 0; i < weekDays.size(); i++) {
            DayOfWeekFragment fragment = new DayOfWeekFragment();
            Bundle bundle = new Bundle();
            bundle.putString("adapter", String.valueOf(i));
            bundle.putString("supervisor", supervisor);
            bundle.putString("room", room);
            bundle.putString("course", course);
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, weekDays.get(i));
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_schedule:
                onBackPressed();
                break;
            case R.id.nav_change_schedule:
                intent = new Intent(MainActivity.this, ChangeScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_exams:
                intent = new Intent(MainActivity.this, ExamsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_notes:
                intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_personalize:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "ePlan Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(AlarmReceiverLesson.CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}