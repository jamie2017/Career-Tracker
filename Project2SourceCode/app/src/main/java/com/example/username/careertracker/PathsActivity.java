package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.username.careertracker.Fragment.PathsFragment;
import com.example.username.menulibrary.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

public class PathsActivity extends AppCompatActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
//    WriteData write;
//    QueryData query;
//    //our database reference object
//    private DatabaseReference userLoginRef;
//    private FirebaseUser roofRef;
//    private FirebaseUser curUser;
//    private FirebaseAuth mAuth;
//    private String uid;
//    final String refURL = "https://careertrackerv2.firebaseio.com/";

    private PathAdapter pAdapter;
    private Intent curInent;
    private String targetPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        curInent = getIntent();
        targetPath = curInent.getStringExtra("path_name");
//        userLoginRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
//        roofRef = FirebaseAuth.getInstance().getCurrentUser();
//        write = new WriteData(userLoginRef,roofRef);
//        query = new QueryData(userLoginRef,roofRef);
//        mAuth = FirebaseAuth.getInstance();
//        curUser = mAuth.getCurrentUser();
//        uid = curUser.getUid();
//        if(!userLoginRef.child("users").child(uid).getKey().equals(uid)) {
//            write.addUser(curUser);
//        }
//        Toast.makeText(getApplicationContext(), userLoginRef.child("users").child(uid).child("email").toString() , Toast.LENGTH_LONG).show();

//        query.getAndUpdateUserProfile();
//        Toast.makeText(getApplicationContext(), "Welcome Back" , Toast.LENGTH_LONG).show();

        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        pAdapter = new PathAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(pAdapter);
        mViewPger.addOnPageChangeListener(pAdapter);

        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPger);
        alphaTabsIndicator.getTabView(0);


    }
    @Override
    protected void onStart() {
        super.onStart();
        pAdapter.getItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do your process here
            startActivity(new Intent(PathsActivity.this, MainActivity.class));

        }
        return super.onKeyDown(keyCode, event);
    }


    private class PathAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"Paths"};

        public PathAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(PathsFragment.newInstance(titles[0], targetPath));
//            Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();

        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (0 == position) {
                alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
            } else if (2 == position) {
                alphaTabsIndicator.getCurrentItemView().removeShow();
            } else if (3 == position) {
                alphaTabsIndicator.removeAllBadge();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }








}
