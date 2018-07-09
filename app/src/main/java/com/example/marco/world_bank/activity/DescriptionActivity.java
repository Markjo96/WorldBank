package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.marco.world_bank.R;

public class DescriptionActivity extends Activity{
    TextView tvIndicatorName;
    TextView tvIndicatorDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);
        tvIndicatorName = findViewById(R.id.tvDesName);
        tvIndicatorDescription = findViewById(R.id.tvDesNote);
        String indicatorName = getIntent().getStringExtra("INDICATOR_NAME");
        String indicatorDescription = getIntent().getStringExtra("INDICATOR_NOTE");
        tvIndicatorName.setText(indicatorName);
        tvIndicatorDescription.setText(indicatorDescription);
        tvIndicatorDescription.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.9), (int) (height*0.5));
    }
}
