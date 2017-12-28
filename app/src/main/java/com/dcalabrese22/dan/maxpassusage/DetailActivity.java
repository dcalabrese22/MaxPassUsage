package com.dcalabrese22.dan.maxpassusage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String name = getIntent().getStringExtra(MainActivity.EXTRA_SKI_AREA_NAME);
        mHeader = findViewById(R.id.tv_ski_area_header);
        mHeader.setText(name);
    }


}
