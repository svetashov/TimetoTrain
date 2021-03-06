package com.example.a2.timetotrain;


import java.util.GregorianCalendar;
import java.util.LinkedList;

public class SleepUnit {
    public static final String[] monthNames = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"};
    private long id;   // Day of this year + year + hourOfEndSleeping
    private GregorianCalendar dateStartOfSleep, dateEndOfSleep;
    private String month, stringDateStart, stringDateEnd, stringHours;   // month name
    private int rate;   // rating of sleep
    private String description;
    private int hoursOfSleeping, minutesOfSleeping, calories;

    public String getStringHours() {
        return stringHours;
    }

    public SleepUnit(GregorianCalendar dateStartOfSleep, GregorianCalendar dateEndOfSleep, int rate, String description) {
        this.dateStartOfSleep = dateStartOfSleep;
        this.dateEndOfSleep = dateEndOfSleep;
        this.rate = rate;
        this.description = description;
        this.id = dateEndOfSleep.get(GregorianCalendar.YEAR) + dateEndOfSleep.get(GregorianCalendar.DAY_OF_YEAR);
        this.month = monthNames[dateEndOfSleep.get(GregorianCalendar.MONTH)];
        this.hoursOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateStartOfSleep.getTimeInMillis()) / 3600000);
        this.minutesOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateStartOfSleep.getTimeInMillis()) % 60000);
        this.calories = (int) (hoursOfSleeping * 70.25);
        this.stringDateEnd = getStringSleepDate(dateEndOfSleep);
        this.stringDateStart = getStringSleepDate(dateStartOfSleep);
        this.stringHours = String.valueOf(dateStartOfSleep.get(GregorianCalendar.HOUR_OF_DAY) + ":" + dateStartOfSleep.get(GregorianCalendar.MINUTE) +
                " - " + dateEndOfSleep.get(GregorianCalendar.HOUR_OF_DAY) + ":" + dateEndOfSleep.get(GregorianCalendar.MINUTE));
    }

    public static String getStringSleepDate(GregorianCalendar c) {
        String date = String.valueOf(c.get(GregorianCalendar.YEAR)) + ":" + String.valueOf(c.get(GregorianCalendar.MONTH)) + ":" + String.valueOf(c.get(GregorianCalendar.DAY_OF_MONTH))
                + ":" + String.valueOf(c.get(GregorianCalendar.HOUR_OF_DAY)) + ":" + String.valueOf(c.get(GregorianCalendar.MINUTE));
        return date;
    }


    public static GregorianCalendar parseStringToCalendar(String date) {
        String[] dateArray = date.split(":");
        int[] values = new int[dateArray.length];
        for (int i = 0; i < dateArray.length; i++) {
            values[i] = Integer.parseInt(dateArray[i]);
        }
        return new GregorianCalendar(values[0], values[1], values[2], values[3], values[4]);

    }

    public boolean isSleepUnitExistInList(LinkedList<SleepUnit> list) {
        for (SleepUnit unit : list) {
            if (unit.getId() == this.getId())
                return true;
        }
        return false;
    }

    public String getStringDateStart() {
        return stringDateStart;
    }

    public String getStringDateEnd() {
        return stringDateEnd;
    }

    public long getId() {
        return id;
    }

    public GregorianCalendar getDateStartOfSleep() {
        return dateStartOfSleep;
    }

    public GregorianCalendar getDateEndOfSleep() {
        return dateEndOfSleep;
    }

    public String getMonth() {
        return month;
    }

    public int getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public int getHoursOfSleeping() {
        return hoursOfSleeping;
    }

    public int getMinutesOfSleeping() {
        return minutesOfSleeping;
    }

    public String getCalories() {
        return String.valueOf(calories);
    }


}
