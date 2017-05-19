package com.example.a2.timetotrain;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a2.timetotrain.data.DBSleeps;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES;


public class SleepFragment extends Fragment {

    private Button buttonEditSleep, buttonNewSleep;
    private TextView textViewMonthDay, textViewYear, textViewLengthSleep, textViewHours, textViewCalories;
    private RatingBar ratingBar;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_sleep, container, false);

        buttonNewSleep = (Button) currentView.findViewById(R.id.button_newSleep);
        buttonEditSleep = (Button) currentView.findViewById(R.id.button_edit_sleep);
        textViewMonthDay = (TextView) currentView.findViewById(R.id.textView_sleep_month_day);
        textViewYear = (TextView) currentView.findViewById(R.id.textView_sleep_year);
        textViewLengthSleep = (TextView) currentView.findViewById(R.id.textView_length_sleep);
        textViewHours = (TextView) currentView.findViewById(R.id.textView_hours_of_sleeping);
        textViewCalories = (TextView) currentView.findViewById(R.id.textView_calories_sleep);
        ratingBar = (RatingBar) currentView.findViewById(R.id.ratingBar_indicator_sleep);
        barChart = (BarChart) currentView.findViewById(R.id.barChart);

        DBSleeps dbHelper = new DBSleeps(getContext());
        LinkedList<SleepUnit> sleepList = dbHelper.selectAll();
        dbHelper.delete(sleepList.get(0).getId());
        dbHelper.insert(new SleepUnit(new GregorianCalendar(2017, 5, 19, 12, 38), new GregorianCalendar(2017, 5, 19, 20, 38), 5 , "Nice sleep"));

        sleepList = dbHelper.selectAll();

        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (SleepUnit sleep : sleepList)
            entries.add(new BarEntry(sleep.getId(), sleep.getHoursOfSleeping()));
        BarDataSet barDataSet = new BarDataSet(entries, "Sleeping");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
        return currentView;
    }


}
