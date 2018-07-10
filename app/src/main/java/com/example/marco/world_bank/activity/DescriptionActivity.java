package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.entities.ImageDao;

public class DescriptionActivity extends Activity{
    TextView tvIndicatorName;
    TextView tvIndicatorDescription;
    ImageView ivScreenShot;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_activity);



        tvIndicatorName = findViewById(R.id.tvDesName);
        tvIndicatorDescription = findViewById(R.id.tvDesNote);
        ivScreenShot = findViewById(R.id.ivScreenShot);
        ivScreenShot.setVisibility(View.GONE);

        //1 description, 2 image view
        int choice = getIntent().getIntExtra("CHOICE",0);
        String keyName = getIntent().getStringExtra("KEY_NAME");
        String name = getIntent().getStringExtra("NAME");
        String note = getIntent().getStringExtra("NOTE");



        if (choice==1){
            tvIndicatorName.setText(name);
            tvIndicatorDescription.setText(note);

            tvIndicatorDescription.setMovementMethod(new ScrollingMovementMethod());
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            getWindow().setLayout((int) (width*0.9), (int) (height*0.5));
        }else{
            //insert image
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            ImageDao imageDao = new ImageDao(keyName,null);
            byte[] bytes = databaseHelper.getImg(imageDao);
            databaseHelper.close();
            ivScreenShot.setVisibility(View.VISIBLE);
            ivScreenShot.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));

        }

    }
}
