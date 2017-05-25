package com.example.a2.timetotrain;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Date;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_DAY;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_ISCOURSE;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_STRING;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;
import static com.example.a2.timetotrain.MainActivity.EXTRAS_MODE_OF_CREATING_COURSE;

public class NewCousre extends AppCompatActivity {

    private Button btnTimePicker, buttonCourse, buttonReady;
    private TextView editTextTime;
    private Spinner spinnerGender, spinnerLevel;
    public static long timeMilis, delayTime;
    private int mHour, mMinute;
    private SharedPreferences mSettings;
    Course course;
    String editTextTimeParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_course);
        setTitle("Создание курса тренировок");

        if (getIntent().getIntExtra(EXTRAS_MODE_OF_CREATING_COURSE, 0) == 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        btnTimePicker = (Button) findViewById(R.id.btn_time);
        buttonCourse = (Button) findViewById(R.id.buttonCourse);
        editTextTime = (TextView) findViewById(R.id.textViewTime);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        buttonReady = (Button) findViewById(R.id.buttonReady);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.sexlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(adapter1);
        course = new Course("Группа мышц", 0, 0, "Время тренировки", 0);
        course.getCourseObject(getIntent());
        timeMilis = course.timeMillis;
        editTextTime.setText(course.time);
        buttonCourse.setText(course.typeOfCourse);
        spinnerGender.setSelection(course.gender);
        spinnerLevel.setSelection(course.level);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course.gender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course.level = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog();

            }
        });
        buttonCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCourse();
            }
        });
        buttonReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready(v);
            }
        });


    }

    protected void ready(View v) {

        // проверка заполнения всех полей
        if (course.time.equals("Время тренировки"))
            Snackbar.make(v, "Выберите время тренировки", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        else if (course.typeOfCourse.equals("Группа мышц"))
            Snackbar.make(v, "Выберите группу мыщц", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        else {
            mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            CurrentTime ct = new CurrentTime(DateFormat.getTimeInstance().format(new Date()));          // Creating object of Class CurrentTime for getting current hour, minute, second
            delayTime = timeMilis - ct.getHour() * 3600000 - ct.getMinute() * 60000 - ct.getSecond() * 1000;  // Calculate the delay between the current and the required time
            if (delayTime < 0) delayTime += AlarmManager.INTERVAL_DAY;
            scheduleNotification(NewCousre.this, delayTime, 101);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean(APP_PREFERENCES_ISCOURSE, true)
                    .putInt(APP_PREFERENCES_COURSE_LEVEL, course.level)
                    .putInt(APP_PREFERENCES_CURRENT_COURSE_GENDER, course.gender)
                    .putString(APP_PREFERENCES_TYPECOURSE, course.typeOfCourse)
                    .putLong(APP_PREFERENCES_TIME_MILLIS, course.timeMillis)
                    .putInt(APP_PREFERENCES_CURRENT_DAY, 1)
                    .putString(APP_PREFERENCES_TIME_STRING, course.time)
                    .apply();
            Intent intent = new Intent(NewCousre.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void timeDialog() {

        CurrentTime ct = new CurrentTime(DateFormat.getTimeInstance().format(new Date()));
        mHour = ct.getHour();
        mMinute = ct.getMinute();
        timeMilis = mHour * 3600000 + mMinute * 60000 + 10 * 60 * 1000;
        TimePickerDialog timePickerDialog = new TimePickerDialog(NewCousre.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeMilis = hourOfDay * 60 * 60 * 1000 + minute * 60 * 1000;        // set required time for train by user
                editTextTimeParam = hourOfDay + " : " + minute;
                course.time = editTextTimeParam;
                course.timeMillis = timeMilis;
                editTextTime.setText(editTextTimeParam);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();

    }

    public void setCourse() {
        Intent intent = new Intent(NewCousre.this, ChooseExerciseGroup.class);
        intent.putExtra(APP_PREFERENCES_TYPECOURSE, course.typeOfCourse);
        intent.putExtra(APP_PREFERENCES_CURRENT_COURSE_GENDER, course.gender);
        intent.putExtra(APP_PREFERENCES_COURSE_LEVEL, course.level);
        intent.putExtra(APP_PREFERENCES_TIME_MILLIS, course.timeMillis);
        intent.putExtra(APP_PREFERENCES_TIME_STRING, course.time);
        startActivity(intent);

    }

    public void scheduleNotification(Context context, long delayTime, int notificationId) {//delayTime is time, selected by user
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.content))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_menu_exercises_dark)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000})
                .setTicker("Время заниматься!");
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Notification notification = builder.build();
        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, delayTime + System.currentTimeMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(NewCousre.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class CurrentTime {
    private static int hour, minute, second;
    private static String[] currentTimeArray;

    public CurrentTime(String currentTime) {
        currentTimeArray = currentTime.split(":");
        hour = Integer.parseInt(currentTimeArray[0]);
        minute = Integer.parseInt(currentTimeArray[1]);
        second = Integer.parseInt(currentTimeArray[2].split(" ")[0]);
    }

    protected int getHour() {
        return hour;
    }

    protected int getMinute() {
        return minute;
    }

    protected int getSecond() {
        return second;
    }

}

