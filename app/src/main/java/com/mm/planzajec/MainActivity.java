package com.mm.planzajec;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DayOfWeekFragment dayOfWeekFragment;
    private DayOfWeekFragment dayOfWeekFragment1;
    private DayOfWeekFragment dayOfWeekFragment2;
    private DayOfWeekFragment dayOfWeekFragment3;
    private DayOfWeekFragment dayOfWeekFragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MessageFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
//        }

        //tablayout
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        dayOfWeekFragment = new DayOfWeekFragment();
        dayOfWeekFragment1 = new DayOfWeekFragment();
        dayOfWeekFragment2 = new DayOfWeekFragment();
        dayOfWeekFragment3 = new DayOfWeekFragment();
        dayOfWeekFragment4 = new DayOfWeekFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
//        String[] name_tab ={"Monday", "Tuesday","Wednesday","Thursday","Friday"};
//        for (int i=0; i<name_tab.length+1; i++){
//            dayOfWeekFragment = new DayOfWeekFragment();
//            viewPagerAdapter.addFragment(dayOfWeekFragment, name_tab[i]);
//        }
        viewPagerAdapter.addFragment(dayOfWeekFragment, "Monday");
        viewPagerAdapter.addFragment(dayOfWeekFragment1, "Tuesday");
        viewPagerAdapter.addFragment(dayOfWeekFragment2, "Wednesday");
        viewPagerAdapter.addFragment(dayOfWeekFragment3, "Thursday");
        viewPagerAdapter.addFragment(dayOfWeekFragment4, "Friday");
        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_message:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_chat:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ChatFragment()).commit();
                intent = new Intent(MainActivity.this, ChangeScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_exams:
                intent = new Intent(MainActivity.this, ExamsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_notes:
                intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new NotesFragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
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

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}