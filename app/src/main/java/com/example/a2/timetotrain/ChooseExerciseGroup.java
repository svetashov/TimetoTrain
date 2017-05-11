package com.example.a2.timetotrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_STRING;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;


public class ChooseExerciseGroup extends AppCompatActivity {

    private ListView listView;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Exercise[] types = Exercise.makeEx(ChooseExerciseGroup.this, "types.txt");
        TypeOfExerciseAdapter adapter = new TypeOfExerciseAdapter(ChooseExerciseGroup.this, types);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        course = new Course();
        course.getCourseObject(getIntent());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course.typeOfCourse = types[position].name;
                Intent intent = new Intent(ChooseExerciseGroup.this, NewCousre.class);
                intent.putExtra(APP_PREFERENCES_TYPECOURSE, course.typeOfCourse);
                intent.putExtra(APP_PREFERENCES_CURRENT_COURSE_GENDER, course.gender);
                intent.putExtra(APP_PREFERENCES_COURSE_LEVEL, course.level);
                intent.putExtra(APP_PREFERENCES_TIME_MILLIS, course.timeMillis);
                intent.putExtra(APP_PREFERENCES_TIME_STRING, course.time);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                course.getCourseObject(getIntent());
                Intent intent = new Intent(ChooseExerciseGroup.this, NewCousre.class);
                intent.putExtra(APP_PREFERENCES_TYPECOURSE, course.typeOfCourse);
                intent.putExtra(APP_PREFERENCES_CURRENT_COURSE_GENDER, course.gender);
                intent.putExtra(APP_PREFERENCES_COURSE_LEVEL, course.level);
                intent.putExtra(APP_PREFERENCES_TIME_MILLIS, course.timeMillis);
                intent.putExtra(APP_PREFERENCES_TIME_STRING, course.time);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}