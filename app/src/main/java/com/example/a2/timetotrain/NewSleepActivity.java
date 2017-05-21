package com.example.a2.timetotrain;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a2.timetotrain.data.DBSleeps;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static com.example.a2.timetotrain.SleepFragment.EXTRAS_LONG_ID_EDITING_SLEEP;
import static com.example.a2.timetotrain.SleepFragment.EXTRAS_MODE;

public class NewSleepActivity extends AppCompatActivity {

    private Button setStartSleep, setEndSleep;
    private TextView textStartSleep, textEndSleep;
    private RatingBar ratingBar;
    private EditText comment;
    private long selectedID;
    public GregorianCalendar dateStartSleep = null, dateEndSleep = null;

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
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_sleep_rating);
        comment = (EditText) findViewById(R.id.editText_comment_for_sleep);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        int mode = getIntent().getIntExtra(EXTRAS_MODE, 0);
        selectedID = getIntent().getLongExtra(EXTRAS_LONG_ID_EDITING_SLEEP, 0);
        if (mode == 0) {
            setTitle("Новая запись");
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dateEndSleep != null && dateStartSleep != null) {
                        SleepUnit sleepUnit = new SleepUnit(dateStartSleep, dateEndSleep, (int) ratingBar.getRating(), comment.getText().toString());
                        final DBSleeps dbHelper = new DBSleeps(NewSleepActivity.this);
                        LinkedList<SleepUnit> currentListInDataBase = dbHelper.selectAll();
                        if (sleepUnit.isSleepUnitExistInList(currentListInDataBase))
                            dbHelper.update(sleepUnit);
                        else dbHelper.insert(sleepUnit);
                        finish();
                    } else Snackbar.make(view, "Выберите время сна", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            setStartSleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        Calendar now = Calendar.getInstance();

                        @Override
                        public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                            if (year <= now.get(Calendar.YEAR) && monthOfYear <= now.get(Calendar.MONTH) && dayOfMonth <= now.get(Calendar.DAY_OF_MONTH)) {
                                Calendar now = Calendar.getInstance();
                                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                        dateStartSleep = timeSet(year, monthOfYear, dayOfMonth, hourOfDay, minute, second, textStartSleep);
                                    }
                                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                                timePickerDialog.show(getFragmentManager(), "Выберите время");
                            }
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show(getFragmentManager(), "Выберите день");
                }
            });
            setEndSleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar now = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                            if (year <= now.get(Calendar.YEAR) && monthOfYear <= now.get(Calendar.MONTH) && dayOfMonth <= now.get(Calendar.DAY_OF_MONTH)) {
                                Calendar now = Calendar.getInstance();
                                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                        dateEndSleep = timeSet(year, monthOfYear, dayOfMonth, hourOfDay, minute, second, textEndSleep);
                                    }
                                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                                timePickerDialog.show(getFragmentManager(), "Выберите время");
                            }
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show(getFragmentManager(), "Выберите день");
                }
            });
        } else {
            setTitle("Изменить запись");
            DBSleeps dbHelper = new DBSleeps(NewSleepActivity.this);
            SleepUnit currentEditableSleep = dbHelper.select(selectedID);
            dateStartSleep = currentEditableSleep.getDateStartOfSleep();
            dateEndSleep = currentEditableSleep.getDateEndOfSleep();
            ratingBar.setRating(currentEditableSleep.getRate());
            comment.setText(currentEditableSleep.getDescription());
            setStartSleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar now = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                            if (year <= now.get(Calendar.YEAR) && monthOfYear <= now.get(Calendar.MONTH) && dayOfMonth <= now.get(Calendar.DAY_OF_MONTH)) {
                                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                        dateStartSleep = timeSet(year, monthOfYear, dayOfMonth, hourOfDay, minute, second, textStartSleep);
                                    }
                                }, dateStartSleep.get(Calendar.HOUR_OF_DAY), dateStartSleep.get(Calendar.MINUTE), true);
                                timePickerDialog.show(getFragmentManager(), "Выберите время");
                            }
                        }
                    }, dateStartSleep.get(Calendar.YEAR), dateStartSleep.get(Calendar.MONTH), dateStartSleep.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show(getFragmentManager(), "Выберите день");
                }
            });
            setEndSleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar now = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                            if (year <= now.get(Calendar.YEAR) && monthOfYear <= now.get(Calendar.MONTH) && dayOfMonth <= now.get(Calendar.DAY_OF_MONTH)) {
                                Calendar now = Calendar.getInstance();
                                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                        dateEndSleep = timeSet(year, monthOfYear, dayOfMonth, hourOfDay, minute, second, textEndSleep);
                                    }
                                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                                timePickerDialog.show(getFragmentManager(), "Выберите время");
                            }
                        }
                    }, dateEndSleep.get(Calendar.YEAR), dateEndSleep.get(Calendar.MONTH), dateEndSleep.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show(getFragmentManager(), "Выберите день");
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dateEndSleep != null && dateStartSleep != null) {
                                SleepUnit sleepUnit = new SleepUnit(dateStartSleep, dateEndSleep, (int) ratingBar.getRating(), comment.getText().toString());
                                final DBSleeps dbHelper = new DBSleeps(NewSleepActivity.this);
                                LinkedList<SleepUnit> currentListInDataBase = dbHelper.selectAll();
                                if (sleepUnit.isSleepUnitExistInList(currentListInDataBase))
                                    dbHelper.update(sleepUnit);
                                else dbHelper.insert(sleepUnit);
                                finish();
                            } else Snackbar.make(view, "Выберите время сна", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    };
                }
            });
        }


    }

    GregorianCalendar timeSet(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second, TextView sleep) {
        sleep.setText(SleepUnit.monthNames[monthOfYear] + " " + dayOfMonth + ", " + hourOfDay + ":" + minute);
        return new GregorianCalendar(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
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
