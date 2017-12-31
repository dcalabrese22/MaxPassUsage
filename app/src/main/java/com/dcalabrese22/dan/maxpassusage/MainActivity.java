package com.dcalabrese22.dan.maxpassusage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dcalabrese22.dan.maxpassusage.database.DbOperations;

public class MainActivity extends AppCompatActivity implements SkiAreaClickHandler{

    private RecyclerView mRecyclerView;
    private SkiAreaAdapter mAdapter;
    private SearchView mSearchView;
    private TextView mTotalPassesUsed;
    private DbOperations mOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOperator = new DbOperations(this);
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(getString(R.string.first_time), true);
        if (isFirstTime) {
            ResortsBuilder builder = new ResortsBuilder(mOperator);
            builder.createSkiAreas();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.first_time), false);
            editor.commit();
        }
        mRecyclerView = findViewById(R.id.rv_ski_area_list);
        mAdapter = new SkiAreaAdapter(this);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(ll);
        mRecyclerView.setAdapter(mAdapter);
        mSearchView = findViewById(R.id.searchview);
        mTotalPassesUsed = findViewById(R.id.tv_total_passes_used);

        mTotalPassesUsed.setText(String.valueOf(mOperator.getTotalPassesUsed()));

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setIconified(false);
            }
        });

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

    @Override
    protected void onResume() {
        mSearchView.setQuery("", true);
        mSearchView.clearFocus();
        mTotalPassesUsed.setText(String.valueOf(mOperator.getTotalPassesUsed()));
        super.onResume();
    }
}
