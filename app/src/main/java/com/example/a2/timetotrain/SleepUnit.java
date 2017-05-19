package com.example.a2.timetotrain;


import java.util.GregorianCalendar;

public class SleepUnit {
    private final String[] monthNames = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"};
    private long id;   // Day of this year + year + hourOfEndSleeping
    private GregorianCalendar dateStartOfSleep, dateEndOfSleep;
    private String month;   // month name
    private int rate;   // rating of sleep
    private String description;
    private int hoursOfSleeping, minutesOfSleeping, calories;

    public SleepUnit(GregorianCalendar dateStartOfSleep, GregorianCalendar dateEndOfSleep, int rate, String description) {
        this.dateStartOfSleep = dateStartOfSleep;
        this.dateEndOfSleep = dateEndOfSleep;
        this.rate = rate;
        this.description = description;
        this.id = dateEndOfSleep.YEAR + dateEndOfSleep.DAY_OF_YEAR;
        this.month = monthNames[dateEndOfSleep.MONTH];
        this.hoursOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateStartOfSleep.getTimeInMillis()) / 3600000);
        this.minutesOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateStartOfSleep.getTimeInMillis()) / 60000);
        this.calories = (int) (hoursOfSleeping * 70.25);
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

    public int getCalories() {
        return calories;
    }


}
