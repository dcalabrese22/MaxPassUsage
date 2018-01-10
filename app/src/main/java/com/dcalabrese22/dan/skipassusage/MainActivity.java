package com.dcalabrese22.dan.skipassusage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcalabrese22.dan.skipassusage.database.DbOperations;

public class MainActivity extends AppCompatActivity implements SkiAreaClickHandler{

    private RecyclerView mRecyclerView;
    private SkiAreaAdapter mAdapter;
    private SearchView mSearchView;
    private TextView mTotalPassesUsed;
    private DbOperations mOperator;
    private Context mContext;
    private LinearLayout mPassesUsedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                    SkiAreaLocationService.PERMISSION_REQUEST_CODE);
        }

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
        mPassesUsedLayout = findViewById(R.id.ll_passes_used);

        mTotalPassesUsed.setText(String.valueOf(mOperator.getTotalPassesUsed()));

        mPassesUsedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PassesUsedActivity.class);
                startActivity(intent);
            }
        });

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
