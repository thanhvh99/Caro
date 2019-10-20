package com.ai.caro;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {

    private static BitmapManager manager = new BitmapManager();

    private Bitmap X;
    private Bitmap O;

    private BitmapManager() {

    }

    public static void initialize(Application application) {
        Resources resources = application.getResources();

        manager.X = BitmapFactory.decodeResource(resources, R.drawable.x);
        manager.O = BitmapFactory.decodeResource(resources, R.drawable.o);
    }

    public static Bitmap getX() {
        return manager.X;
    }

    public static Bitmap getO() {
        return manager.O;
    }

}
