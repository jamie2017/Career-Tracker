package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCompanies extends AppCompatActivity implements View.OnClickListener{

    WriteData write;
    QueryData query;
    //our database reference object
    private DatabaseReference userLoginRef;
    private FirebaseUser roofRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";
    private Button submit;
    private AutoCompleteTextView comName;
    private AutoCompleteTextView contactName;
    private AutoCompleteTextView contactEmail;
    private Spinner jobTitle;
    private ImageButton buttonScanSave;
    private Intent curIntent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companies);

        userLoginRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        roofRef = FirebaseAuth.getInstance().getCurrentUser();
        write = new WriteData(userLoginRef,roofRef);
        query = new QueryData(userLoginRef,roofRef);
        submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(this);

        curIntent = getIntent();
        String emailFromScan = curIntent.getStringExtra("receiver_email");

        comName = (AutoCompleteTextView)findViewById(R.id.company_name);
        comName.setText(curIntent.getStringExtra("CompanyName"));
        contactName = (AutoCompleteTextView)findViewById(R.id.company_contacts);
        contactEmail = (AutoCompleteTextView)findViewById(R.id.contacts_email);
        if (contactEmail.getText() == null && emailFromScan != null) {
            contactEmail.setText(emailFromScan);
        }
        jobTitle = (Spinner) findViewById(R.id.JobTitle);
        buttonScanSave = (ImageButton) findViewById(R.id.scan_card);
        buttonScanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(AddCompanies.this, ScanCard.class);
                nextIntent.putExtra("next_jump","addCompany");
                startActivity(nextIntent);
//                startActivity(new Intent(AddCompanies.this, ScanCard.class));
            }
        });



    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button2) {
            saveContact();
            saveCompany();


        }
    }



    private void saveCompany() {
        final String comNameStr = comName.getText().toString().toUpperCase();
        String comId = query.getCompany(comNameStr);
        if (comId != null) {
            write.addNewCompany(comNameStr,comId);
        } else {
            write.addNewCompany(comNameStr);
        }
        Toast.makeText(getApplicationContext(), comNameStr+" is Added", Toast.LENGTH_LONG).show();

    }

    private void saveContact() {
        String comNameStr = comName.getText().toString();
        String contactNameStr = contactName.getText().toString();
        String contactEmailStr = contactEmail.getText().toString();
        String jobTitleStr = jobTitle.getSelectedItem().toString();
        write.addNewContact(contactNameStr,jobTitleStr,contactEmailStr,comNameStr);
        Toast.makeText(getApplicationContext(), contactNameStr + " is Added", Toast.LENGTH_LONG).show();
    }
}



