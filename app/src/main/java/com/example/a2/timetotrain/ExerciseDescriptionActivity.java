package com.example.a2.timetotrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    private CardView cardViewUpper;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        Exercise exercise = Exercise.getExerciseFromName(ExerciseDescriptionActivity.this, getIntent().getStringExtra(EXTRAS_SELECTED_NAME));
        cardViewUpper = (CardView)findViewById(R.id.card_view_name_exercise);
        name = (TextView) findViewById(R.id.textView_counts_exercise);
        description = (TextView) findViewById(R.id.textView_description_exercise);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView_exercise);
        cardViewUpper.setVisibility(View.GONE);
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
