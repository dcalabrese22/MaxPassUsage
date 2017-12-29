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

    public Cursor getSkiPassesUsed(String skiAreaName) {
        String[] columns = {DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE};
        String selection = DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME + "=?";
        String[] selectionArgs = {skiAreaName};
        return mDatabase.query(DbContract.SkiAreaEntry.SKI_AREA_TABLE, columns, selection,
                selectionArgs, null, null, null, null);
    }

    public Cursor getColumns() {
        return mDatabase.query(DbContract.SkiAreaEntry.SKI_AREA_TABLE, null,
                null, null, null, null, null);
    }

    public int updatePassesUsed(String name, int newValue) {
        ContentValues contentValues = new ContentValues();
        String where = DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME + "=?";
        String[] args = {String.valueOf(name)};
        contentValues.put(DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE, newValue);
        return mDatabase.update(DbContract.SkiAreaEntry.SKI_AREA_TABLE, contentValues,
                where, args);
    }

    public double[] getLatAndLong(String name) {
        String[] columns = {DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LATITUDE,
                DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LONGITUDE};
        String selection = DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME + "=?";
        String[] args = {name};
        Cursor cursor = mDatabase.query(DbContract.SkiAreaEntry.SKI_AREA_TABLE, columns, selection,
                args, null, null, null);
        cursor.moveToFirst();
        double lat = cursor.getDouble(cursor.getColumnIndex
                (DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex
                (DbContract.SkiAreaEntry.SKI_AREA_COLUMN_LONGITUDE));
        cursor.close();
        double[] coords = {lat, longitude};
        return coords;
    }
}
