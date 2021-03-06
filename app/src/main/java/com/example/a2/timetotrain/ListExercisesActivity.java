package com.example.a2.timetotrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.a2.timetotrain.ExerciseFragment.EXTRAS_NAME_OF_FILE;
import static com.example.a2.timetotrain.MainActivity.EXTRAS_SELECTED_NAME;

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
        listView = (ListView) findViewById(R.id.list_view_exercises);
        Context context = ListExercisesActivity.this;
        final Exercise[] exercises = Exercise.makeEx(context, Exercise.getParsingFileName(takedIntent.getStringExtra(EXTRAS_NAME_OF_FILE)));
        listView.setAdapter(new TypeOfExerciseAdapter(context, exercises));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListExercisesActivity.this, ExerciseDescriptionActivity.class);
                intent.putExtra(EXTRAS_SELECTED_NAME, exercises[position].name);
                startActivity(intent);
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
