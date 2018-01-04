package com.dcalabrese22.dan.skipassusage;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dcalabrese22.dan.skipassusage.database.DbContract;
import com.dcalabrese22.dan.skipassusage.database.DbOperations;

import java.util.ArrayList;
import java.util.List;

public class PassesUsedActivity extends AppCompatActivity {

    private DbOperations mOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passes_used);

        mOperator = new DbOperations(this);
        Cursor cursor = mOperator.queryPassesUsed();
        List<SkiArea> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex
                    (DbContract.SkiAreaEntry.SKI_AREA_COLUMN_NAME));
            int passesUsed = cursor.getInt(cursor.getColumnIndex
                    (DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE));
            SkiArea area = new SkiArea(name, passesUsed);
            results.add(area);
        }
        cursor.close();

        RecyclerView recyclerView = findViewById(R.id.rv_passes_used_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PassesUsedAdapter adapter = new PassesUsedAdapter(results);
        recyclerView.setAdapter(adapter);

    }
}
