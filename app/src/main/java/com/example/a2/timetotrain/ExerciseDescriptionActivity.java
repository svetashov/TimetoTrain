package com.example.a2.timetotrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import static com.example.a2.timetotrain.MainActivity.EXTRAS_SELECTED_NAME;

public class ExerciseDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Exercise exercise = Exercise.getExerciseFromName(ExerciseDescriptionActivity.this, getIntent().getStringExtra(EXTRAS_SELECTED_NAME));
        Log.i("EXERCISE", Boolean.toString(exercise == null));
        if (exercise != null){
            setTitle(exercise.name);

        }
    }

}
