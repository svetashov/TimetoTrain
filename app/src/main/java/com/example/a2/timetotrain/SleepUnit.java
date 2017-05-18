package com.example.a2.timetotrain;


import java.util.GregorianCalendar;

public class SleepUnit {
    final String[] monthNames = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"};
    private int id;     // Day of this year + year + hourOfEndSleeping
    private GregorianCalendar dateStartOfSleep, dateEndOfSleep;
    private int day, year;   // from dateEndOfSleep
    private String month;   // month name
    private int rate;   // rating of sleep
    private String description;
    private int hoursOfSleeping, minutesOfSleeping;


    public SleepUnit(GregorianCalendar dateStartOfSleep, GregorianCalendar dateEndOfSleep, int rate, String description) {
        this.dateStartOfSleep = dateStartOfSleep;
        this.dateEndOfSleep = dateEndOfSleep;
        this.rate = rate;
        this.description = description;

        this.hoursOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateEndOfSleep.getTimeInMillis()) / 3600000);
        this.minutesOfSleeping = (int) ((dateEndOfSleep.getTimeInMillis() - dateEndOfSleep.getTimeInMillis()) / 60000);
        this.day = dateEndOfSleep.DAY_OF_MONTH;
        this.year = dateEndOfSleep.YEAR;
        this.month = monthNames[dateEndOfSleep.MONTH];

        this.id = dateEndOfSleep.DAY_OF_YEAR + this.year + dateEndOfSleep.HOUR_OF_DAY;
    }


    // Getters and Setters

    public String getMonth() {
        return month;
    }

    public int getId() {
        return id;
    }

    public GregorianCalendar getDateStartOfSleep() {
        return dateStartOfSleep;
    }

    public void setDateStartOfSleep(GregorianCalendar dateStartOfSleep) {
        this.dateStartOfSleep = dateStartOfSleep;
    }

    public GregorianCalendar getDateEndOfSleep() {
        return dateEndOfSleep;
    }

    public void setDateEndOfSleep(GregorianCalendar dateEndOfSleep) {
        this.dateEndOfSleep = dateEndOfSleep;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHoursOfSleeping() {
        return hoursOfSleeping;
    }

    public int getMinutesOfSleeping() {
        return minutesOfSleeping;
    }

}
