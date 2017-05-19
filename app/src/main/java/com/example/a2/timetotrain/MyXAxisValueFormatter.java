package com.example.a2.timetotrain;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.GregorianCalendar;
import java.util.LinkedList;


public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private LinkedList<SleepUnit> sleepList;

    public MyXAxisValueFormatter(LinkedList<SleepUnit> sleepList) {
        this.sleepList = sleepList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        String dayOnX = "";
        for (SleepUnit sleepUnit : sleepList)
            if (sleepUnit.getId() == (long) value)
                dayOnX = String.valueOf(sleepUnit.getDateEndOfSleep().get(GregorianCalendar.DAY_OF_MONTH));
        return dayOnX;
    }

}
