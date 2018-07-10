package com.example.marco.world_bank;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import java.io.ByteArrayOutputStream;


public class ScreenShot{
    Context context;

    public ScreenShot(Context context) {
        this.context = context;
    }

    public static Bitmap takeScreenshot(View v){
            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache(true);
            Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(true);
            return b;
        }
        public static Bitmap takescreenshotOfRootView(View v){
            return takeScreenshot(v.getRootView());
        }
        //convert
    public static byte[] getBytes(Bitmap b) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    }

