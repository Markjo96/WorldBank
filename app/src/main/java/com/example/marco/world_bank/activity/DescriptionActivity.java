package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.entities.ImageDao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescriptionActivity extends Activity{
    private static final int REQUEST =  1;
    private TextView tvIndicatorName;
    private TextView tvIndicatorDescription;
    private ImageView ivScreenShot;
    private Context context = this;
    private Button btnShare;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_activity);



        tvIndicatorName = findViewById(R.id.tvDesName);
        tvIndicatorDescription = findViewById(R.id.tvDesNote);
        ivScreenShot = findViewById(R.id.ivScreenShot);
        ivScreenShot.setVisibility(View.GONE);
        btnShare = findViewById(R.id.btnShare);

        //1 description, 2 image view
        int choice = getIntent().getIntExtra("CHOICE",0);
        String keyName = getIntent().getStringExtra("KEY_NAME");
        String name = getIntent().getStringExtra("NAME");
        String note = getIntent().getStringExtra("NOTE");



        if (choice==1){
            //description
            tvIndicatorName.setText(name);
            tvIndicatorDescription.setText(note);

            tvIndicatorDescription.setMovementMethod(new ScrollingMovementMethod());
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                getWindow().setLayout((int) (width*0.9), (int) (height*0.5));
            }else{
                getWindow().setLayout((int) (width*0.6), (int) (height*0.9));
            }



        }else{
            //insert image
            btnShare.setVisibility(View.VISIBLE);
            btnShare.setOnClickListener(listenerShare);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            ImageDao imageDao = new ImageDao(keyName,null);
            byte[] bytes = databaseHelper.getImg(imageDao);
            databaseHelper.close();
            ivScreenShot.setVisibility(View.VISIBLE);
            ivScreenShot.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));

        }

    }

    View.OnClickListener listenerShare = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (!hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST );
                } else {
                    //do here
                    shareImg();
                }
            } else {
                //do here
                shareImg();
            }

        }

    };


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareImg();
                } else {
                    Toast.makeText(context, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void shareImg(){
        View content = findViewById(R.id.ivScreenShot);
        content.setDrawingCacheEnabled(true);

        Bitmap bitmap = content.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        startActivity(Intent.createChooser(share, "Share Image"));

    }


}
