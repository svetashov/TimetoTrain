package com.example.a2.timetotrain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Constant name of the file with the settings
    public static final String APP_PREFERENCES = "mySettings";
    // Parameter that will be saved in the settings
    public static final String APP_PREFERENCES_ISCOURSE = "isCourse";
    public static final String APP_PREFERENCES_CURRENT_COURSE_GENDER = "gender";
    public static final String APP_PREFERENCES_COURSE_LEVEL = "level";
    public static final String APP_PREFERENCES_TYPECOURSE = "typeCourse";
    public static final String APP_PREFERENCES_TIME_MILLIS = "timeMillis";
    public static final String APP_PREFERENCES_TIME_STRING = "timeString";
    public static final String APP_PREFERENCES_CURRENT_DAY = "currentDay";
    public static final String EXTRAS_SELECTED_NAME = "extras_selected_name";
    // Default value of the parameter
    public static boolean iscourse = false;
    // Instance of the configuration class
    private SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting settings
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        // Get the value from settings
        iscourse = mSettings.getBoolean(APP_PREFERENCES_ISCOURSE, false);
        if (!iscourse) {        // If the current course is not found, then the Activity with the creation of the course
            Intent intent = new Intent(MainActivity.this, NewCousre.class);
            startActivity(intent);

        }

        setContentView(R.layout.activity_main);


        // set first Fragment
        Fragment fragment = null;
        Class fragmentClass = CurrentCourse.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer1.closeDrawer(GravityCompat.START);
        // set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_course);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_course) {
            fragmentClass = CurrentCourse.class;
            setFragment(fragmentClass, fragment, item);
        } else if (id == R.id.nav_sleeping) {

        } else if (id == R.id.nav_exercises) {
            fragmentClass = ExerciseFragment.class;
            setFragment(fragmentClass, fragment, item);
        } else if (id == R.id.nav_new_course) {
            Intent intent = new Intent(MainActivity.this, NewCousre.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }


        return true;
    }

    public void setFragment(Class fragmentClass, Fragment fragment, MenuItem item) {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Вставляем фрагмент, заменяя текущий фрагмент
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Saving data
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_ISCOURSE, iscourse);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_ISCOURSE)) {
            // Get the value from settings
            iscourse = mSettings.getBoolean(APP_PREFERENCES_ISCOURSE, false);
        }
    }
}
