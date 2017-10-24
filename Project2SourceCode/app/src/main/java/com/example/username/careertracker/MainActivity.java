package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.username.careertracker.Fragment.AllPathsFragment;
import com.example.username.careertracker.Fragment.ApplicationsFragment;
import com.example.username.careertracker.Fragment.ContactsFragment;
import com.example.username.careertracker.Fragment.MeFragment;
import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.example.username.menulibrary.AlphaTabsIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
    WriteData write;
    QueryData query;
    //our database reference object
    private DatabaseReference userLoginRef;
    private FirebaseUser roofRef;
    private FirebaseUser curUser;
    private FirebaseAuth mAuth;
    private String uid;
    final String refURL = "https://careertrackerv2.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userLoginRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        roofRef = FirebaseAuth.getInstance().getCurrentUser();
        write = new WriteData(userLoginRef,roofRef);
//        query = new QueryData(userLoginRef,roofRef);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        uid = curUser.getUid();
        if(!userLoginRef.child("users").child(uid).getKey().equals(uid)) {
            write.addUser(curUser);
        }
//        Toast.makeText(getApplicationContext(), userLoginRef.child("users").child(uid).child("userEmail").getKey() , Toast.LENGTH_LONG).show();

//        query.getAndUpdateUserProfile();
//        Toast.makeText(getApplicationContext(), "Welcome Back" , Toast.LENGTH_LONG).show();

        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPger);

    }



    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"Applications", "Contacts", "Paths", "Me"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(ApplicationsFragment.newInstance(titles[0]));
            fragments.add(ContactsFragment.newInstance(titles[1]));
            fragments.add(AllPathsFragment.newInstance(titles[2]));
            fragments.add(MeFragment.newInstance(titles[3]));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_applications:
                startActivity(new Intent(MainActivity.this, EditApplications.class));
                return true;
            case R.id.track_paths:
                startActivity(new Intent(MainActivity.this,PieActivity.class));
//                startActivity(new Intent(MainActivity.this,DemoLikeTumblrActivity.class));
                return true;
            case R.id.add_companies:
                startActivity(new Intent(MainActivity.this, AddCompanies.class));
                return true;
            case R.id.scan_card:
                startActivity(new Intent(MainActivity.this, ScanCard.class));
                return true;
            case R.id.send_email:
                startActivity(new Intent(MainActivity.this, SendEmail.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }


}
