package com.example.a2.timetotrain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.a2.timetotrain.ExerciseFragment.EXTRAS_NAME_OF_FILE;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_STRING;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;

public class ListExercisesActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent takedIntent = getIntent();
        setTitle(takedIntent.getStringExtra(EXTRAS_NAME_OF_FILE));
        listView = (ListView)findViewById(R.id.list_view_exercises);
        Context context = ListExercisesActivity.this;
        listView.setAdapter(new TypeOfExerciseAdapter(context, Exercise.makeEx(context, Exercise.getParsingFileName(takedIntent.getStringExtra(EXTRAS_NAME_OF_FILE)))));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
