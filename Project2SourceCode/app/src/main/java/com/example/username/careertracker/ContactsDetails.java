package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ContactsDetails extends AppCompatActivity {

    private TextView contactViewTitle;
    private TextView comName;
    private TextView contactName;
    private TextView contactEmail;
    private TextView contactTitle;
//    private TextView remark;
    private FloatingActionButton sendEmail;

    private Intent curIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        curIntent = getIntent();
        contactViewTitle = (TextView)findViewById(R.id.ContactViewTitle);
        contactViewTitle.setText(curIntent.getStringExtra("contactViewTitle"));
        comName = (TextView)findViewById(R.id.comNameVal);
        comName.setText(curIntent.getStringExtra("comName"));
        contactName = (TextView)findViewById(R.id.contactNameVal);
        contactName.setText(curIntent.getStringExtra("contactName"));
        contactEmail = (TextView)findViewById(R.id.contactEmailVal);
        contactEmail.setText(curIntent.getStringExtra("contactEmail"));
        contactTitle = (TextView)findViewById(R.id.contactTitleVal);
        contactTitle.setText(curIntent.getStringExtra("contactTitle"));
        sendEmail = (FloatingActionButton)findViewById(R.id.followup_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver_email = contactEmail.getText().toString();
                Intent followupInent = new Intent(ContactsDetails.this,SendEmail.class);
                followupInent.putExtra("receiver_email",receiver_email);
                startActivity(followupInent);
            }
        });
//        remark = (TextView)findViewById(R.id.textView36);
//        remark.setText(curIntent.getStringExtra("remark"));


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do your process here
            startActivity(new Intent(ContactsDetails.this, MainActivity.class));

        }
        return super.onKeyDown(keyCode, event);
    }

}
