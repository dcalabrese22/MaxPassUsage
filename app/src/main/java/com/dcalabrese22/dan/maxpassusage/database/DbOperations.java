package com.dcalabrese22.dan.maxpassusage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcalabrese22.dan.maxpassusage.SkiArea;

/**
 * Created by dan on 12/19/17.
 */

public class DbOperations {

    private SQLiteDatabase mDatabase;

    public DbOperations(Context context) {
        DbApplication application = (DbApplication) context.getApplicationContext();
        mDatabase = application.getConnection();
    }

    public long insertSkiArea(SkiArea skiArea) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME, skiArea.getResortName());
        contentValues.put(DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LATITUDE, skiArea.getLatitude());
        contentValues.put(DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LONGITUDE, skiArea.getLongitude());
        contentValues.put(DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE, skiArea.getTimesGone());
        return mDatabase.insert(DbContract.SkiAreaEntry.SKI_AREA_TABLE, null, contentValues);
    }

    public Cursor getColumns() {
        return mDatabase.query(DbContract.SkiAreaEntry.SKI_AREA_TABLE, null, null, null, null, null, null);
    }
}
