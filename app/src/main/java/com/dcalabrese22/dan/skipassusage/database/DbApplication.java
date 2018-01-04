package com.dcalabrese22.dan.skipassusage.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dan on 12/19/17.
 */

public class DbApplication extends Application {

    private DbHelper mDbHelper;

    public DbApplication() {
        mDbHelper = new DbHelper(this);
    }

    public SQLiteDatabase getConnection() {
        return mDbHelper.getWritableDatabase();
    }
}
