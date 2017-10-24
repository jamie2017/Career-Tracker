package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.username.careertracker.Model.ApplicationStack;
import com.example.username.careertracker.Model.ApplicationStep;
import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;

public class EditApplications extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Sample";
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private SwitchDateTimeDialogFragment dateTimeFragment;

    private AutoCompleteTextView actv_company_name;
    private AutoCompleteTextView actv_contacts;

    private String[] company_name;
    private HashSet<String> companyList;

    private String[] company_contacts;
    private HashSet<String> contactList;

    private String  appStackId;


    WriteData write;
    QueryData query;
    //our database reference object
    private DatabaseReference userLoginRef;
    private FirebaseUser roofRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";

    private ApplicationStack appStack;

    private Button save;
    private AutoCompleteTextView cName;
    private Spinner jobType;
    private Spinner posType;
    private AutoCompleteTextView hrName;
    private EditText remark;
    private CheckBox important;
    private Spinner jobSource;
    private Spinner jobStage;
    private Button timeButton;
    private Button delete;
    private EditText curRounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_applications);

        userLoginRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        roofRef = FirebaseAuth.getInstance().getCurrentUser();
        write = new WriteData(userLoginRef, roofRef);
        query = new QueryData(userLoginRef, roofRef);

        Intent curIntent = getIntent();
        String tmpCName = curIntent.getStringExtra("CompanyName");

        final EditText etRmark = (EditText) findViewById(R.id.Remark);
        timeButton = (Button) findViewById(R.id.dateButton);

        etRmark.setScroller(new Scroller(getApplicationContext()));
        etRmark.setVerticalScrollBarEnabled(true);
        etRmark.setMinLines(2);
        etRmark.setMaxLines(3);

        company_contacts = getContactList();
        company_name = getCompanyList();


        actv_company_name = (AutoCompleteTextView) findViewById(R.id.company_name);
        actv_contacts = (AutoCompleteTextView) findViewById(R.id.company_contacts);

        ArrayAdapter adapter_company = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, company_name);

        ArrayAdapter adapter_contacts = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, company_contacts);

        actv_company_name.setAdapter(adapter_company);
        actv_company_name.setThreshold(1);

        actv_contacts.setAdapter(adapter_contacts);
        actv_contacts.setThreshold(1);

        if (savedInstanceState != null) {
            // Restore value from saved state
            timeButton.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }

        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(R.string.positive_button_datetime_picker),
                    getString(R.string.negative_button_datetime_picker)
            );
        }
        // Assign values we want
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MAY, 4, 13, 00).getTime());
        // Or assign each element, default element is the current moment
//         dateTimeFragment.setDefaultHourOfDay(13);
//         dateTimeFragment.setDefaultMinute(00);
//         dateTimeFragment.setDefaultDay(4);
//         dateTimeFragment.setDefaultMonth(Calendar.MAY);
//         dateTimeFragment.setDefaultYear(2017);

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }
        // Set listener for date
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
//                textView.setText(myDateFormat.format(date));
                timeButton.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                timeButton.setText("");
            }
        });


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });


        save = (Button) findViewById(R.id.button6);
        save.setOnClickListener(this);

        cName = (AutoCompleteTextView) findViewById(R.id.company_name);
        if (tmpCName != null) {
            cName.setText(tmpCName);
        }
        jobType = (Spinner) findViewById(R.id.spinner2);
        posType = (Spinner) findViewById(R.id.position_type);
        hrName = (AutoCompleteTextView) findViewById(R.id.company_contacts);
        remark = (EditText) findViewById(R.id.Remark);
        important = (CheckBox) findViewById(R.id.checkBox7);
        jobSource = (Spinner) findViewById(R.id.Job_Source);
        jobStage = (Spinner) findViewById(R.id.cur_step);
        delete = (Button) findViewById(R.id.button5);
        delete.setOnClickListener(this);
        curRounds = (EditText)findViewById(R.id.rounds);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putCharSequence(STATE_TEXTVIEW, timeButton.getText());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button6) {
            saveApplication(appStack);
//            PathsFragment.newInstance("Paths");

            Intent nextIntent = new Intent(EditApplications.this, PathsActivity.class);
            nextIntent.putExtra("path_name",appStackId);

            startActivity(nextIntent);

        } else if (i == R.id.button5) {
            startActivity(new Intent(EditApplications.this, MainActivity.class));
        }

    }

    private void saveApplication(ApplicationStack appStack) {

        String cNameStr = trimString(cName.getText().toString().toUpperCase());
        String jobTypeStr = trimString(jobType.getSelectedItem().toString().toUpperCase());
        String posTypeStr = trimString(posType.getSelectedItem().toString().toUpperCase());
        String jobSourceStr = trimString(jobSource.getSelectedItem().toString().toUpperCase());
        String interviewTime = timeButton.getText().toString();
        String year = interviewTime.split(" ")[2];
        appStackId = cNameStr +" " + jobTypeStr+" "+posTypeStr+" " + year;
        appStack = query.existApplicationStack(appStackId);
        if (appStack == null) {
            appStack = write.createNewApplicationStack(appStackId);
        } else {
            // update last step status to pass
        }


//        String comId = query.getCompany(cNameStr);
//        if (comId == null || comId == "") {
//            switch (comId = write.addNewCompany(cNameStr)) {
//            }
//        }
        Toast.makeText(getApplicationContext(), cNameStr, Toast.LENGTH_LONG).show();
        String jobStageStr = jobStage.getSelectedItem().toString().toUpperCase()+curRounds.getText().toString();
        String appId = jobStageStr;
        Toast.makeText(getApplicationContext(), jobStageStr, Toast.LENGTH_LONG).show();


        String contactEmail = hrName.getText().toString();
        String remarkStr = remark.getText().toString();
        boolean importantStr = important.isChecked();

//        Toast.makeText(getApplicationContext(), interviewTime, Toast.LENGTH_LONG).show();

        ApplicationStep app = write.addSingleApplication(appId,cNameStr,remarkStr,jobTypeStr,jobStageStr,interviewTime,importantStr,posTypeStr,jobSourceStr,contactEmail
                );
        //appId,comName,jobType,curStep,interviewTime,important,posType,jobSource,contactEmail
        write.appendNewApplicationNode(appStack, appId, app);

        Toast.makeText(getApplicationContext(), "New Application Added", Toast.LENGTH_LONG).show();

    }

    private String trimString(String input) {
        if (input != null || input != "") {
            return input.trim();
        } else {
            return input;
        }
    }

    public String[] getCompanyList() {
        companyList = new HashSet<>();
        DatabaseReference targetComRef = query.userRef.child("company");
        targetComRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("comName")) {
                        String c = (String) childKey.getValue();
                        companyList.add(c);
                    }
                }
                return;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();

            }
        });
        for(String tmp: companyList.toArray(new String[companyList.size()])) {
            Log.i(TAG, tmp + "@");
        }
        return companyList.toArray(new String[companyList.size()]);
    }

    public String[] getContactList() {
        contactList = new HashSet<>();
        DatabaseReference targetContactRef = query.userRef.child("contacts");
        targetContactRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("email")) {
                        String c = (String) childKey.getValue();
                        contactList.add(c);
                    }
                }
                return;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();

            }
        });
        for(String tmp: contactList.toArray(new String[contactList.size()])) {
            Log.i(TAG, tmp + "@");
        }
        return contactList.toArray(new String[contactList.size()]);


    }
}