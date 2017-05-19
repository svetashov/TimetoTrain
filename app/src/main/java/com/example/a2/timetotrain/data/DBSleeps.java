package com.example.a2.timetotrain.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a2.timetotrain.SleepUnit;

import java.util.GregorianCalendar;
import java.util.LinkedList;


public class DBSleeps {
    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "tableSleeps";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE_START = "dateStart";
    private static final String COLUMN_DATE_END = "dateEnd";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_RATE = "rate";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_DATE_START = 1;
    private static final int NUM_COLUMN_DATE_END = 2;
    private static final int NUM_COLUMN_DESCRIPTION = 3;
    private static final int NUM_COLUMN_RATE = 4;

    private SQLiteDatabase mDataBase;

    public DBSleeps(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(SleepUnit sleep) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, sleep.getId());
        cv.put(COLUMN_DATE_START, sleep.getStringDateStart());
        cv.put(COLUMN_DATE_END, sleep.getStringDateEnd());
        cv.put(COLUMN_DESCRIPTION, sleep.getDescription());
        cv.put(COLUMN_RATE, sleep.getRate());
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(SleepUnit sleep) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, sleep.getId());
        cv.put(COLUMN_DATE_START, sleep.getStringDateStart());
        cv.put(COLUMN_DATE_END, sleep.getStringDateEnd());
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
        GregorianCalendar dateStart = SleepUnit.parseStringToCalendar(mCursor.getString(NUM_COLUMN_DATE_START));
        GregorianCalendar dateEnd = SleepUnit.parseStringToCalendar(mCursor.getString(NUM_COLUMN_DATE_END));
        int rate = mCursor.getInt(NUM_COLUMN_RATE);
        String description = mCursor.getString(NUM_COLUMN_DESCRIPTION);
        mCursor.close();
        return new SleepUnit(dateStart, dateEnd, rate, description);
    }

    public LinkedList<SleepUnit> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null,
                null);
        LinkedList<SleepUnit> arr = new LinkedList<SleepUnit>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                GregorianCalendar dateStart = SleepUnit.parseStringToCalendar(mCursor.getString(NUM_COLUMN_DATE_START));
                GregorianCalendar dateEnd = SleepUnit.parseStringToCalendar(mCursor.getString(NUM_COLUMN_DATE_END));
                int rate = mCursor.getInt(NUM_COLUMN_RATE);
                String description = mCursor.getString(NUM_COLUMN_DESCRIPTION);
                arr.add(new SleepUnit(dateStart, dateEnd, rate, description));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_DATE_START + " TEXT, " +
                    COLUMN_DATE_END + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_RATE + " INTEGER);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
