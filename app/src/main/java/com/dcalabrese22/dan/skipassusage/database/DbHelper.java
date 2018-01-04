package com.dcalabrese22.dan.skipassusage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dan on 12/19/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "maxpassusage.db";
    public static final int DATABASE_VERSION = 6;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SKI_AREA_TABLE =
                "Create Table " + DbContract.SkiAreaEntry.SKI_AREA_TABLE + " (" +
                        DbContract.SkiAreaEntry._ID + " Integer Primary Key AutoIncrement, " +
                        DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME + " Text Not Null Unique, " +
                        DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LATITUDE + "  Real Not Null, " +
                        DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LONGITUDE + " Real Not Null, " +
                        DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE + " Integer Not Null" +
                        ");";

        Log.d("Database onCreate", "called");
        sqLiteDatabase.execSQL(SQL_CREATE_SKI_AREA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("onUpgrade", "called");
        String drop = "Drop table if exists " + DbContract.SkiAreaEntry.SKI_AREA_TABLE;
        sqLiteDatabase.execSQL(drop);
        onCreate(sqLiteDatabase);
    }
}
