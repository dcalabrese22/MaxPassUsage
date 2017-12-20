package com.dcalabrese22.dan.maxpassusage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dcalabrese22.dan.maxpassusage.database.DbContract;
import com.dcalabrese22.dan.maxpassusage.database.DbOperations;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbOperations operator = new DbOperations(this);
        ResortsBuilder builder = new ResortsBuilder(operator);
        builder.createSkiAreas();
        Cursor cursor = operator.getColumns();
        String[] name = cursor.getColumnNames();
        for (int i = 0; i < name.length; i++) {
            Log.d("Column", name[i]);
        }
    }
}
