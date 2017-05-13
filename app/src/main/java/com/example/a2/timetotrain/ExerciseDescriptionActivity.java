package com.example.a2.timetotrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.example.a2.timetotrain.MainActivity.EXTRAS_SELECTED_NAME;

public class ExerciseDescriptionActivity extends AppCompatActivity {

    private TextView name, description;
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Exercise exercise = Exercise.getExerciseFromName(ExerciseDescriptionActivity.this, getIntent().getStringExtra(EXTRAS_SELECTED_NAME));
        name = (TextView) findViewById(R.id.textView_name_ofExercise);
        description = (TextView) findViewById(R.id.textView_description_Of_exercise);
        gifImageView = (GifImageView) findViewById(R.id.gif_image_description);
        if (exercise != null) {
            setTitle(exercise.name);
            name.setText(exercise.name);
            description.setText(exercise.description);
            try {
                InputStream inputStream = getAssets().open(exercise.gifPath);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                gifImageView.setBytes(bytes);
                gifImageView.startAnimation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
