package com.example.a2.timetotrain;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.a2.timetotrain.CurrentCourse.EXTRAS_INDEX_EXERCISE;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_CURRENT_DAY;
import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TIME_STRING;

public class RecreationActivity extends AppCompatActivity {

    private int index;
    private TextView textView_timer, textView;
    private Button buttonAddTime;
    private long time;
    private CountDownTimer timer;
    private SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreation);
        textView_timer = (TextView) findViewById(R.id.textView_timer);
        buttonAddTime = (Button) findViewById(R.id.button_add_time);
        textView = (TextView) findViewById(R.id.textView_recreationText);

        index = getIntent().getIntExtra(EXTRAS_INDEX_EXERCISE, 0);
        if (index < 3) {
            timer = new CountDownTimer(30000, 1000) { // default value (x, y)   x = 30000; y = 1000
                @Override
                public void onTick(long millisUntilFinished) {
                    textView_timer.setText(textSeconds((int) (millisUntilFinished / 1000)));
                    time = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(RecreationActivity.this, ExerciseActivity.class);
                    intent.putExtra(EXTRAS_INDEX_EXERCISE, index + 1);
                    startActivity(intent);
                }
            }.start();

            buttonAddTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (time < 300000) {
                        timer.cancel();
                        timer = new CountDownTimer(time + 15000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                textView_timer.setText(textSeconds((int) (millisUntilFinished / 1000)));
                                time = millisUntilFinished;
                            }

                            @Override
                            public void onFinish() {
                                Intent intent = new Intent(RecreationActivity.this, ExerciseActivity.class);
                                intent.putExtra(EXTRAS_INDEX_EXERCISE, index + 1);
                                startActivity(intent);
                            }
                        }.start();
                    }
                }
            });
        } else {
            mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            textView.setText("Следующая тренировка завтра в " + mSettings.getString(APP_PREFERENCES_TIME_STRING, ""));
            buttonAddTime.setText("Продолжить");
            textView_timer.setVisibility(View.GONE);
            buttonAddTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = mSettings.getInt(APP_PREFERENCES_CURRENT_DAY, 1);
                    mSettings.edit().putInt(APP_PREFERENCES_CURRENT_DAY, day + 1).apply();
                    startActivity(new Intent(RecreationActivity.this, MainActivity.class));
                }
            });
        }
    }

    String textSeconds(int seconds) {
        String text;
        int last = seconds % 10;
        if (seconds >= 10 && seconds <= 20 || last == 0 || last >= 5 && last <= 9)
            text = seconds + " секунд";
        else if (last >= 2 && last <= 4)
            text = seconds + " секунды";
        else
            text = seconds + " секунду";
        return text;
    }

    // on Back pressed Alert: are you sure?
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        openQuitDialog();
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                RecreationActivity.this);
        quitDialog.setTitle("Прервать тренировку?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RecreationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.setCancelable(true);
        quitDialog.show();
    }
}
