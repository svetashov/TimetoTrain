package com.example.a2.timetotrain;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a2.timetotrain.data.DBSleeps;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class SleepFragment extends Fragment {

    private Button buttonEditSleep, buttonNewSleep;
    private TextView textViewMonthDay, textViewYear, textViewLengthSleep, textViewHours, textViewCalories, textViewDescription;
    private RatingBar ratingBar;
    private BarChart barChart;
    private RelativeLayout layoutNoneSleep, layoutSleep;

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
        textViewDescription = (TextView) currentView.findViewById(R.id.textView_description_of_sleep);
        ratingBar = (RatingBar) currentView.findViewById(R.id.ratingBar_indicator_sleep);
        barChart = (BarChart) currentView.findViewById(R.id.barChart);
        layoutNoneSleep = (RelativeLayout) currentView.findViewById(R.id.layout_none_sleep);
        layoutSleep = (RelativeLayout) currentView.findViewById(R.id.layout_sleep_data);
        ratingBar.setIsIndicator(true);

        final DBSleeps dbHelper = new DBSleeps(getContext());
        dbHelper.deleteAll();
        //dbHelper.insert(new SleepUnit(new GregorianCalendar(2017, 4, 21, 11, 20), new GregorianCalendar(2017, 4, 21, 16, 20), 0, ""));
        LinkedList<SleepUnit> sleepList = dbHelper.selectAll();
        SleepUnit todaySleep = new SleepUnit(new GregorianCalendar(), new GregorianCalendar(), 0, "");
        if (!todaySleep.isSleepUnitExistInList(sleepList)) {
            dbHelper.insert(todaySleep);
        }
        else
            todaySleep = dbHelper.select(todaySleep.getId());
        sleepList = dbHelper.selectAll();
        itemSelected(todaySleep);

        if (sleepList.size() != 0) {

            /** adding data to bar chart */
            List<BarEntry> entries = new ArrayList<BarEntry>();
            for (SleepUnit sleep : sleepList)
                entries.add(new BarEntry(sleep.getId(), sleep.getHoursOfSleeping()));
            final BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setHighLightColor(ColorTemplate.rgb("0288d1"));
            barDataSet.setColor(ColorTemplate.rgb("03a9f4"));
            final BarData barData = new BarData(barDataSet);

            barData.setBarWidth(.1f);
            barChart.setData(barData);
            barChart.getLegend().setEnabled(false);
            barChart.setDrawGridBackground(false);
            barChart.setDrawBorders(false);
            barChart.getAxisRight().setEnabled(false);
            barChart.setVisibleXRangeMaximum(5);
            barChart.setMaxVisibleValueCount(0);
            barChart.moveViewToX(sleepList.get(entries.size() - 1).getId());
            barChart.getDescription().setEnabled(false);
            barChart.setScaleEnabled(false);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new MyXAxisValueFormatter(sleepList));
            xAxis.setGranularity(1);
            xAxis.setTextColor(R.color.textColorPrimary);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);

            YAxis yAxis = barChart.getAxisLeft();
            yAxis.setGranularity(1f);
            yAxis.setTextColor(R.color.textColorPrimary);
            yAxis.setAxisLineColor(R.color.colorBackground);
            yAxis.setDrawGridLines(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setAxisMinimum(0);
            barChart.invalidate();
            barChart.animateY(800);
            barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    SleepUnit sleepUnit = dbHelper.select((long) e.getX());
                    itemSelected(sleepUnit);
                }

                @Override
                public void onNothingSelected() {

                }
            });

        }

        /** button listeners */
        buttonNewSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewSleepActivity.class);
                startActivity(intent);
            }
        });

        return currentView;
    }
    void itemSelected(SleepUnit unit){
        if (unit.getHoursOfSleeping() + unit.getHoursOfSleeping() != 0) {
            layoutNoneSleep.setVisibility(View.GONE);
            layoutSleep.setVisibility(View.VISIBLE);
            textViewMonthDay.setText(unit.getMonth() + ", " + String.valueOf(unit.getDateEndOfSleep().get(GregorianCalendar.DAY_OF_MONTH)));
            textViewYear.setText(String.valueOf(unit.getDateEndOfSleep().get(GregorianCalendar.YEAR)));
            String lengthSleep = "";
            if (unit.getMinutesOfSleeping() != 0)
                lengthSleep = unit.getHoursOfSleeping() + getActivity().getString(R.string.hours) + " " + String.valueOf(unit.getMinutesOfSleeping()) + getActivity().getString(R.string.minutes);
            else lengthSleep = unit.getHoursOfSleeping() + getActivity().getString(R.string.hours);
            textViewLengthSleep.setText(lengthSleep);
            textViewHours.setText(unit.getStringHours());
            textViewCalories.setText(unit.getCalories());
            if (unit.getRate() != 0)
                ratingBar.setRating(unit.getRate());
            else ratingBar.setVisibility(View.GONE);
            if (unit.getDescription() != "")
                textViewDescription.setText(unit.getDescription());
            else textViewDescription.setVisibility(View.GONE);
        } else {
            layoutSleep.setVisibility(View.GONE);
            layoutNoneSleep.setVisibility(View.VISIBLE);
            textViewMonthDay.setText(unit.getMonth() + ", " + String.valueOf(unit.getDateEndOfSleep().get(GregorianCalendar.DAY_OF_MONTH)));
            textViewYear.setText(String.valueOf(unit.getDateEndOfSleep().get(GregorianCalendar.YEAR)));
        }
    }


}
