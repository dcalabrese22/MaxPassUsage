package com.dcalabrese22.dan.skipassusage.database;

import android.provider.BaseColumns;

/**
 * Created by dan on 12/19/17.
 */

public class DbContract {

    public static class SkiAreaEntry implements BaseColumns {
        public static final String SKI_AREA_TABLE = "ski_area";

        public static final String SKI_AREA_COLUMN_NAME = "name";
        public static final String SKI_AREA_COLUMN_LATITUDE = "latitude";
        public static final String SKI_AREA_COLUMN_LONGITUDE = "longitude";
        public static final String SKI_AREA_COLUMN_TIMES_GONE = "times_gone";
    }
}
