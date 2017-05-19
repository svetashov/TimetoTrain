package com.example.a2.timetotrain.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a2.timetotrain.SleepUnit;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Артем 2 on 19.05.2017.
 */

public class DBSleeps {
    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tableSleeps";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_YEAR_START = "YearStart";
    private static final String COLUMN_MONTH_START = "MonthStart";
    private static final String COLUMN_DAY_START = "DayStart";
    private static final String COLUMN_HOUR_START = "HourStart";
    private static final String COLUMN_MINUTE_START = "MinuteStart";
    private static final String COLUMN_YEAR_END = "YearEnd";
    private static final String COLUMN_MONTH_END = "MonthEnd";
    private static final String COLUMN_DAY_END = "DayEnd";
    private static final String COLUMN_HOUR_END = "HourEnd";
    private static final String COLUMN_MINUTE_END = "MinuteEnd";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_RATE = "Rate";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_YEAR_START = 1;
    private static final int NUM_COLUMN_MONTH_START = 2;
    private static final int NUM_COLUMN_DAY_START = 3;
    private static final int NUM_COLUMN_HOUR_START = 4;
    private static final int NUM_COLUMN_MINUTE_START = 5;
    private static final int NUM_COLUMN_YEAR_END = 6;
    private static final int NUM_COLUMN_MONTH_END = 7;
    private static final int NUM_COLUMN_DAY_END = 8;
    private static final int NUM_COLUMN_HOUR_END = 9;
    private static final int NUM_COLUMN_MINUTE_END = 10;
    private static final int NUM_COLUMN_DESCRIPTION = 11;
    private static final int NUM_COLUMN_RATE = 12;

    private SQLiteDatabase mDataBase;

    public DBSleeps(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(SleepUnit sleep) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, sleep.getId());
        cv.put(COLUMN_YEAR_START, sleep.getDateStartOfSleep().YEAR);
        cv.put(COLUMN_MONTH_START, sleep.getDateStartOfSleep().MONTH);
        cv.put(COLUMN_DAY_START, sleep.getDateStartOfSleep().DAY_OF_MONTH);
        cv.put(COLUMN_HOUR_START, sleep.getDateStartOfSleep().HOUR_OF_DAY);
        cv.put(COLUMN_MINUTE_START, sleep.getDateStartOfSleep().MINUTE);
        cv.put(COLUMN_YEAR_END, sleep.getDateEndOfSleep().YEAR);
        cv.put(COLUMN_MONTH_END, sleep.getDateEndOfSleep().MONTH);
        cv.put(COLUMN_DAY_END, sleep.getDateEndOfSleep().DAY_OF_MONTH);
        cv.put(COLUMN_HOUR_END, sleep.getDateEndOfSleep().HOUR_OF_DAY);
        cv.put(COLUMN_MINUTE_END, sleep.getDateEndOfSleep().MINUTE);
        cv.put(COLUMN_DESCRIPTION, sleep.getDescription());
        cv.put(COLUMN_RATE, sleep.getRate());
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(SleepUnit sleep) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_YEAR_START, sleep.getDateStartOfSleep().YEAR);
        cv.put(COLUMN_MONTH_START, sleep.getDateStartOfSleep().MONTH);
        cv.put(COLUMN_DAY_START, sleep.getDateStartOfSleep().DAY_OF_MONTH);
        cv.put(COLUMN_HOUR_START, sleep.getDateStartOfSleep().HOUR_OF_DAY);
        cv.put(COLUMN_MINUTE_START, sleep.getDateStartOfSleep().MINUTE);
        cv.put(COLUMN_YEAR_END, sleep.getDateEndOfSleep().YEAR);
        cv.put(COLUMN_MONTH_END, sleep.getDateEndOfSleep().MONTH);
        cv.put(COLUMN_DAY_END, sleep.getDateEndOfSleep().DAY_OF_MONTH);
        cv.put(COLUMN_HOUR_END, sleep.getDateEndOfSleep().HOUR_OF_DAY);
        cv.put(COLUMN_MINUTE_END, sleep.getDateEndOfSleep().MINUTE);
        cv.put(COLUMN_DESCRIPTION, sleep.getDescription());
        cv.put(COLUMN_RATE, sleep.getRate());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{
                String.valueOf(sleep.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{
                String.valueOf(id)});
    }

    public SleepUnit select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new
                String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        GregorianCalendar dateStart = new GregorianCalendar(mCursor.getInt(NUM_COLUMN_YEAR_START), mCursor.getInt(NUM_COLUMN_MONTH_START), mCursor.getInt(NUM_COLUMN_DAY_START),
                mCursor.getInt(NUM_COLUMN_HOUR_START), mCursor.getInt(NUM_COLUMN_MINUTE_START));
        GregorianCalendar dateEnd = new GregorianCalendar(mCursor.getInt(NUM_COLUMN_YEAR_END), mCursor.getInt(NUM_COLUMN_MONTH_END), mCursor.getInt(NUM_COLUMN_DAY_END),
                mCursor.getInt(NUM_COLUMN_HOUR_END), mCursor.getInt(NUM_COLUMN_MINUTE_END));
        int rate = mCursor.getInt(NUM_COLUMN_RATE);
        String description = mCursor.getString(NUM_COLUMN_DESCRIPTION);
        return new SleepUnit(dateStart, dateEnd, rate, description);
    }

    public ArrayList<SleepUnit> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null,
                null);
        ArrayList<SleepUnit> arr = new ArrayList<SleepUnit>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                GregorianCalendar dateStart = new GregorianCalendar(mCursor.getInt(NUM_COLUMN_YEAR_START), mCursor.getInt(NUM_COLUMN_MONTH_START), mCursor.getInt(NUM_COLUMN_DAY_START),
                        mCursor.getInt(NUM_COLUMN_HOUR_START), mCursor.getInt(NUM_COLUMN_MINUTE_START));
                GregorianCalendar dateEnd = new GregorianCalendar(mCursor.getInt(NUM_COLUMN_YEAR_END), mCursor.getInt(NUM_COLUMN_MONTH_END), mCursor.getInt(NUM_COLUMN_DAY_END),
                        mCursor.getInt(NUM_COLUMN_HOUR_END), mCursor.getInt(NUM_COLUMN_MINUTE_END));
                int rate = mCursor.getInt(NUM_COLUMN_RATE);
                String description = mCursor.getString(NUM_COLUMN_DESCRIPTION);
                arr.add(new SleepUnit(dateStart, dateEnd, rate, description));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_YEAR_START + " INT, " +
                    COLUMN_MONTH_START + " INT, " +
                    COLUMN_DAY_START + " INT," +
                    COLUMN_HOUR_START + " INT, " +
                    COLUMN_MINUTE_START + " INT, " +
                    COLUMN_YEAR_END + " INT, " +
                    COLUMN_MONTH_END + " INT, " +
                    COLUMN_DAY_END + " INT," +
                    COLUMN_HOUR_END + " INT, " +
                    COLUMN_MINUTE_END + " INT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_RATE + " INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
