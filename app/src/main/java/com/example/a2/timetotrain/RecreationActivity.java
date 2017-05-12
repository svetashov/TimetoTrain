package com.example.a2.timetotrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.a2.timetotrain.CurrentCourse.EXTRAS_INDEX_EXERCISE;

public class RecreationActivity extends AppCompatActivity {

    private int index;
    private TextView textView_timer;
    private Button buttonAddTime;
    private  long time;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView_timer = (TextView)findViewById(R.id.textView_timer);
        buttonAddTime = (Button) findViewById(R.id.button_add_time);

        index = getIntent().getIntExtra(EXTRAS_INDEX_EXERCISE, 0);
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView_timer.setText(textSeconds((int)(millisUntilFinished / 1000)));
                time = millisUntilFinished;
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(RecreationActivity.this, ExerciseActivity.class);
                intent.putExtra(EXTRAS_INDEX_EXERCISE, index+1);
                startActivity(intent);
            }
        }.start();

        buttonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer = new CountDownTimer(time + 15000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textView_timer.setText(textSeconds((int)(millisUntilFinished / 1000)));
                    }
                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(RecreationActivity.this, ExerciseActivity.class);
                        intent.putExtra(EXTRAS_INDEX_EXERCISE, index+1);
                        startActivity(intent);
                    }
                }.start();
            }
        });
    }

    String textSeconds(int seconds){
        String text;
        int last = seconds % 10;
        if (seconds >= 10 && seconds <= 20 || last == 0 || last >= 5 && last <= 9)
            text = seconds + " секунд";
        else if (last >= 2 && last <=4)
            text = seconds + " секунды";
        else
            text = seconds + " секунду";
        return text;
    }

}
