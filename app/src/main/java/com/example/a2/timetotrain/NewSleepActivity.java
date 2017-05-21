package com.example.a2.timetotrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import static com.example.a2.timetotrain.SleepFragment.EXTRAS_LONG_ID_EDITING_SLEEP;
import static com.example.a2.timetotrain.SleepFragment.EXTRAS_MODE;

public class NewSleepActivity extends AppCompatActivity {

    private Button setStartSleep, setEndSleep;
    private TextView textStartSleep, textEndSleep;
    private RatingBar ratingBar;
    private EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sleep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setStartSleep = (Button) findViewById(R.id.button_start_sleep);
        setEndSleep = (Button) findViewById(R.id.button_end_sleep);
        textStartSleep = (TextView) findViewById(R.id.textView_start_sleeping_indicator);
        textEndSleep = (TextView) findViewById(R.id.textView_end_sleeping_indicator);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_indicator_sleep);
        comment = (EditText) findViewById(R.id.editText_comment_for_sleep);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        int mode = getIntent().getIntExtra(EXTRAS_MODE, 0);

        if (mode == 0){
            setTitle("Новая запись");
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createSleep();
                }
            });
        }
        else {
            setTitle("Изменить запись");
            editSleep(getIntent().getLongExtra(EXTRAS_LONG_ID_EDITING_SLEEP, 0));
        }
    }

    void createSleep(){
    }
    void editSleep(long sleepId){}

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
