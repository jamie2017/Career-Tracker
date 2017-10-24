package com.example.username.careertracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SammiFu on 4/20/17.
 */

public class FragmentAllocation extends Fragment{
        public static final String BUNDLE_TITLE = "title";
        private String mTitle = "DefaultValue";

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
        }
        switch (mTitle){
            case "Applications":
                return inflater.inflate(R.layout.fragment_applications, container, false);
            case "Contacts":
//                return inflater.inflate(R.layout.fragment_contacts, container, false);
                return inflater.inflate(R.layout.add_com_activity_list, container, false);
            case "Paths":
                return inflater.inflate(R.layout.fragment_paths, container, false);
            case "Me":
                return inflater.inflate(R.layout.fragment_me, container, false);
        }
            return inflater.inflate(R.layout.fragment_applications, container, false);
    }

        public static FragmentAllocation newInstance(String title) {
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_TITLE, title);
            FragmentAllocation fragment = new FragmentAllocation();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

