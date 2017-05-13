package com.example.a2.timetotrain;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_STRING;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;

public class Course {
    String typeOfCourse, time;
    int gender, level;
    long timeMillis;

    public Course() {
    }

    public Course(String typeOfCourse, int level, int gender, String time, long timeMillis) {
        this.typeOfCourse = typeOfCourse;
        this.level = level;
        this.gender = gender;
        this.time = time;
        this.timeMillis = timeMillis;
    }

    public Course getCourseObject(Intent intent) {
        if (intent.hasExtra(APP_PREFERENCES_TYPECOURSE)) {
            this.typeOfCourse = intent.getStringExtra(APP_PREFERENCES_TYPECOURSE);
            this.gender = intent.getIntExtra(APP_PREFERENCES_CURRENT_COURSE_GENDER, 0);
            this.level = intent.getIntExtra(APP_PREFERENCES_COURSE_LEVEL, 0);
            this.timeMillis = intent.getLongExtra(APP_PREFERENCES_TIME_MILLIS, 0);
            this.time = intent.getStringExtra(APP_PREFERENCES_TIME_STRING);
        }
        return this;
    }
}
