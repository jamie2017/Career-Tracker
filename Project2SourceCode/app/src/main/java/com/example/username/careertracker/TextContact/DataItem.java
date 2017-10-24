package com.example.username.careertracker.TextContact;

/**
 * Created by JMYE on 4/28/17.
 */

import android.graphics.drawable.Drawable;

public class DataItem {

    private String label;

    private Drawable drawable;

    private int navigationInfo;

    public DataItem(String label, Drawable drawable, int navigationInfo) {
        this.label = label;
        this.drawable = drawable;
        this.navigationInfo = navigationInfo;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public int getNavigationInfo() {
        return navigationInfo;
    }
}
