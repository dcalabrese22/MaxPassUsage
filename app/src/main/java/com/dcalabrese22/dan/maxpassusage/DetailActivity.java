package com.dcalabrese22.dan.maxpassusage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dcalabrese22.dan.maxpassusage.database.DbContract;
import com.dcalabrese22.dan.maxpassusage.database.DbOperations;

public class DetailActivity extends AppCompatActivity {

    private TextView mHeader;
    private TextView mPassesUsed;
    private DbOperations mOperator;
    private FloatingActionButton mPlus;
    private FloatingActionButton mMinus;
    private String mName;
    private Context mContext;
    private Button mStartNav;
    private Button mMountainInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = this;
        mName = getIntent().getStringExtra(MainActivity.EXTRA_SKI_AREA_NAME);
        mHeader = findViewById(R.id.tv_ski_area_header);
        mPassesUsed = findViewById(R.id.tv_passes_used);
        mPlus = findViewById(R.id.plus_one);
        mMinus = findViewById(R.id.minus_one);
        mStartNav = findViewById(R.id.btn_navigate);
        mMountainInfo = findViewById(R.id.btn_mountain_info);

        mHeader.setText(mName);
        mOperator = new DbOperations(this);
        Cursor cursor = mOperator.getSkiPassesUsed(mName);

        cursor.moveToFirst();
        int passesUsed = cursor.getInt(cursor.getColumnIndex
                (DbContract.SkiAreaEntry.SKI_AREA_COLUMN_TIMES_GONE));

        cursor.close();

        mPassesUsed.setText(String.valueOf(passesUsed));

        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = Integer.parseInt(mPassesUsed.getText().toString());
                int newValue = current + 1;
                if (newValue < 0) {
                    return;
                } else if (newValue > 5) {
                    Toast.makeText(mContext, R.string.not_more_than_5, Toast.LENGTH_SHORT).show();
                } else {
                    mPassesUsed.setText(String.valueOf(newValue));
                    int updated = mOperator.updatePassesUsed(mName, newValue);
                    Log.d("Updated ", String.valueOf(updated) + " row");
                }
            }
        });

        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = Integer.parseInt(mPassesUsed.getText().toString());
                int newValue = current - 1;
                if (newValue < 0) {
                    return;
                } else if (newValue > 5) {
                    Toast.makeText(mContext, R.string.not_more_than_5, Toast.LENGTH_SHORT).show();
                } else {
                    mPassesUsed.setText(String.valueOf(newValue));
                    int updated = mOperator.updatePassesUsed(mName, newValue);
                    Log.d("Updated ", String.valueOf(updated) + " row");
                }
            }
        });

        mStartNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[] coords = mOperator.getLatAndLong(mName);
                String lat = String.valueOf(coords[0]);
                String lon = String.valueOf(coords[1]);
//                Uri uri = Uri.parse("google.navigation:q=" + lat + "," + lon);
                Uri uri = Uri.parse("google.navigation:q=" + mName);
                Log.d("uri", uri.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        mMountainInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.google.com/#q=" + mName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }




}
