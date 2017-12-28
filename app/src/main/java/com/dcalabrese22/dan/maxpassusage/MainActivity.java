package com.dcalabrese22.dan.maxpassusage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.dcalabrese22.dan.maxpassusage.database.DbOperations;

public class MainActivity extends AppCompatActivity implements SkiAreaClickHandler{

    private RecyclerView mRecyclerView;
    private SkiAreaAdapter mAdapter;
    private SearchView mSearchView;

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

        mRecyclerView = findViewById(R.id.rv_ski_area_list);
        mAdapter = new SkiAreaAdapter(this);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(ll);
        mRecyclerView.setAdapter(mAdapter);
        mSearchView = findViewById(R.id.searchview);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }

    public static final String EXTRA_SKI_AREA_NAME = "ski_area_name";
    @Override
    public void onSkiAreaClick(String area) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_SKI_AREA_NAME, area);
        startActivity(intent);
    }
}
