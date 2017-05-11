package com.example.a2.timetotrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.example.a2.timetotrain.CurrentCourse.EXTRAS_INDEX_EXERCISE;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_DAY;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;


public class ExerciseActivity extends AppCompatActivity {

    private int index, day, gender;
    private SharedPreferences mSettings;
    private String parsingFile;
    private GifImageView gifImageView;
    private TextView textViewName, textViewDescription, textViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_confirm);
        textViewName = (TextView) findViewById(R.id.textView_exercise_name);
        textViewDescription = (TextView) findViewById(R.id.textView_description_exercise);
        textViewCount = (TextView) findViewById(R.id.textView_counts_exercise);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView_exercise);

        // получаем данные о текущем курсе, дне и упражнениях, а также индекс текущего упражнения
        index = getIntent().getIntExtra(EXTRAS_INDEX_EXERCISE, 0);
        setTitle("Упражнение " + (index + 1) + " из 4");
        mSettings = ExerciseActivity.this.getSharedPreferences(APP_PREFERENCES, ExerciseActivity.this.MODE_PRIVATE);
        Course c = new Course(mSettings.getString(APP_PREFERENCES_TYPECOURSE, ""),
                mSettings.getInt(APP_PREFERENCES_COURSE_LEVEL, 1), mSettings.getInt(APP_PREFERENCES_CURRENT_COURSE_GENDER, 1),
                "00:00", mSettings.getLong(APP_PREFERENCES_TIME_MILLIS, 1));
        day = mSettings.getInt(APP_PREFERENCES_CURRENT_DAY, 0);
        int stage = day / 7;
        switch (mSettings.getString(APP_PREFERENCES_TYPECOURSE, "")) {
            case "Пресс":
                parsingFile = "press.txt";
            case "Тренировка всего тела":
                parsingFile = "allBody.txt";
            default:
                parsingFile = "press.txt";
        }
        Exercise[] arrayOfExercises = Exercise.makeEx(ExerciseActivity.this, parsingFile);
        final Exercise[] currentExercises = new Exercise[4];
        System.arraycopy(arrayOfExercises, stage * 4, currentExercises, 0, 4);
        gender = c.gender;
        Exercise exercise = currentExercises[index];

        // вводим данные в элементы
        textViewName.setText(exercise.name);

        // правильное наклонение
        String count_ex = "";
        int nowCountOfExercises = (int)(exercise.course[gender][day - 1]*(c.level+1)*0.7);
        int counter = nowCountOfExercises % 10;
        if (exercise.typeTrain == 0) {
            if (counter >= 5 && counter <= 9 || counter >= 0 && counter <= 1 || nowCountOfExercises >= 12 && nowCountOfExercises <= 21)
                count_ex = "Выполните упражение " + nowCountOfExercises + " раз";
            else if (counter >= 2 && counter <= 4)
                count_ex = "Выполните упражнение " + nowCountOfExercises + " раза";

        } else {
            if (nowCountOfExercises == 11 || counter != 1)
                count_ex = "Выполняте упражнение в течение " + nowCountOfExercises + " секунд";
            else if (counter == 1)
                count_ex = "Выполняте упражнение в течение " +nowCountOfExercises + " секунды";
        }
        textViewCount.setText(count_ex);
        textViewDescription.setText(exercise.description);
        try {
            InputStream inputStream = getAssets().open(exercise.gifPath);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (index == 3) {
                    mSettings.edit().putInt(APP_PREFERENCES_CURRENT_DAY, day+1).apply();
                    Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
