package com.example.jasycdell3.rentalpal;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.HashMap;

/**
 * Created by JASYC DELL 3 on 6/13/2016.
 */
public class Config {
    Activity context;

    public Config(Activity context) {

        this.context = context;
    }

    public double getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    public void maping() {
        HashMap<Integer, String> integerStringHashMap = new HashMap<>();
        integerStringHashMap.put(0, "");
        integerStringHashMap.put(1, "");
        integerStringHashMap.put(2, "");
        integerStringHashMap.put(3, "");
        integerStringHashMap.put(4, "");
        integerStringHashMap.put(5, "");
        integerStringHashMap.put(6, "");
        integerStringHashMap.put(7, "");
        integerStringHashMap.put(8, "");

    }


}
