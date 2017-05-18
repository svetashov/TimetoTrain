package com.example.a2.timetotrain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;


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

        return currentView;
    }

}
