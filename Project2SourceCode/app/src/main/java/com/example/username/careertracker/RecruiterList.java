package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class RecruiterList extends AppCompatActivity {
    private TextView recruiterTV;
    private String R_details;
    private TextView rListTitle;
    private TableRow recruiterTR;
    private String receiver_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_list);
        recruiterTV = (TextView) findViewById(R.id.recruiter_description);
        rListTitle = (TextView) findViewById(R.id.R_list_title);
        recruiterTR = (TableRow) findViewById(R.id.recruiter_1);
        R_details = "Jamie Ye\n"+"Google\n"+"+14801111111\n"+"jamie.ye4@gmail.com\n"+"Software Developer\n";
        receiver_email = R_details.split("\n")[3];
        recruiterTV.setText(R_details);
        rListTitle.setText("Google");
        recruiterTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(RecruiterList.this, SendEmail.class);
                nextIntent.putExtra("receiver_email",receiver_email);
                startActivity(nextIntent);
            }
        });

    }
}
