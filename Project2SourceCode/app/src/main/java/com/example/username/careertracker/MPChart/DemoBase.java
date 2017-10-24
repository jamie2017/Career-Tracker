package com.example.username.careertracker.MPChart;

/**
 * Created by JMYE on 4/29/17.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.username.careertracker.R;

/**
 * Baseclass of all Activities of the Demo Application.
 *
 * @author Philipp Jahoda
 */
public abstract class DemoBase extends FragmentActivity {


    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties ;
//            = new String[] {
//            "Adobe",
//            "Airbnb",
//            "Amazon",
//            "Apple",
//            "Cisco",
//            "Dell",
//            "Dropbox",
//            "eBay",
//            "Facebook",
//            "Google",
//            "HP",
//            "IBM",
//            "Intel",
//            "Linkedin",
//            "Marketo",
//            "Microsoft",
//            "NetApp",
//            "Nokia",
//            "Oracle",
//            "Paypal",
//            "Pinterest",
//            "Qualcomm",
//            "Salesforce",
//            "Samsung",
//            "SAP",
//            "Slack",
//            "Symantec",
//            "Uber",
//            "WalmartLab",
//            "WMware",
//            "Yahoo",
//            "Yelp"};



    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");


    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }


}
