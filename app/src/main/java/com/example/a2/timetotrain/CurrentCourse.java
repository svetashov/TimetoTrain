package com.example.a2.timetotrain;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_COURSE_LEVEL;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_COURSE_GENDER;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_DAY;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_MILLIS;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;


public class CurrentCourse extends Fragment {
    private TextView textViewDay, textViewExercise1, textViewExercise2, textViewExercise3, textViewExercise4, textViewCount1, textViewCount2, textViewCount3, textViewCount4, textViewLeftDays, textViewProgress;
    private Button buttonStart;
    private ProgressBar progressBar;
    private SharedPreferences mSettings;
    private int day;
    private String parsingFile;
    public static final String EXTRAS_INDEX_EXERCISE = "indexExercises";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_current_course, container, false);
        // создаем объект класса Course из сохраненный данных
        mSettings = getContext().getSharedPreferences(APP_PREFERENCES, getContext().MODE_PRIVATE);
        Course c = new Course(mSettings.getString(APP_PREFERENCES_TYPECOURSE, ""),
                mSettings.getInt(APP_PREFERENCES_COURSE_LEVEL, 1), mSettings.getInt(APP_PREFERENCES_CURRENT_COURSE_GENDER, 1),
                "00:00", mSettings.getLong(APP_PREFERENCES_TIME_MILLIS, 1));
        day = mSettings.getInt(APP_PREFERENCES_CURRENT_DAY, 1);
        int stage = day / 7;
        parsingFile = Exercise.getParsingFileName(mSettings.getString(APP_PREFERENCES_TYPECOURSE, ""));
        Exercise[] arrayOfExercises = Exercise.makeEx(getContext(), parsingFile);
        final Exercise[] currentExercises = new Exercise[4];
        System.arraycopy(arrayOfExercises, stage * 4, currentExercises, 0, 4);
        final int gender = c.gender;
        String[] counts = new String[4];
        for (int i = 0; i < 4; i++) {
            counts[i] = String.valueOf(currentExercises[i].course[gender][day - 1] * (c.level + 1));
            if (currentExercises[i].typeTrain==1)
                counts[i] += "с";
        }

        textViewDay = (TextView) v.findViewById(R.id.textView_day);
        textViewCount1 = (TextView) v.findViewById(R.id.textView_count1);
        textViewCount2 = (TextView) v.findViewById(R.id.textView_count2);
        textViewCount3 = (TextView) v.findViewById(R.id.textView_count3);
        textViewCount4 = (TextView) v.findViewById(R.id.textView_count4);
        textViewExercise1 = (TextView) v.findViewById(R.id.textView_exercise1);
        textViewExercise2 = (TextView) v.findViewById(R.id.textView_exercise2);
        textViewExercise3 = (TextView) v.findViewById(R.id.textView_exercise3);
        textViewExercise4 = (TextView) v.findViewById(R.id.textView_exercise4);
        textViewLeftDays = (TextView) v.findViewById(R.id.textView_leftDays);
        textViewProgress = (TextView) v.findViewById(R.id.text_view_completed);
        buttonStart = (Button) v.findViewById(R.id.button_startTraining);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar_completed);
        textViewExercise1.setText(currentExercises[0].name);
        textViewExercise2.setText(currentExercises[1].name);
        textViewExercise3.setText(currentExercises[2].name);
        textViewExercise4.setText(currentExercises[3].name);
        textViewCount1.setText(counts[0]);
        textViewCount2.setText(counts[1]);
        textViewCount3.setText(counts[2]);
        textViewCount4.setText(counts[3]);
        textViewDay.setText("День " + day);
        textViewLeftDays.setText(leftDays(28 - day + 1));
        textViewProgress.setText("Выполнено " + String.valueOf((day - 1) * 100 / 28) + "%");
        progressBar.setProgress((day - 1) * 100 / 28);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExerciseActivity.class);
                intent.putExtra(EXTRAS_INDEX_EXERCISE, 0);
                startActivity(intent);
            }
        });
        return v;
    }

    public static String leftDays(int day) {
        String string = "Осталось " + day + " дней";
        if (day % 10 == 1)
            string = "Остался " + day + " день";
        else if (day % 10 >= 2 && day % 10 <= 4)
            string = "Осталось " + day + " дня";
        else if (day % 10 >= 5 && day % 10 <= 9 || day % 10 == 0)
            string = "Осталось " + day + " дней";
        else if (day >= 11 && day <= 20) string = "Осталось " + day + " дней";
        return string;
    }


}
