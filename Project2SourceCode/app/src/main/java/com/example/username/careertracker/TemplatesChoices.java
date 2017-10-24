package com.example.username.careertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TemplatesChoices extends AppCompatActivity {

    private TextView yourEmail;
    private Intent curIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates_choices);

        curIntent = getIntent();
        yourEmail = (TextView)findViewById(R.id.yourEmail);
        yourEmail.setText(curIntent.getStringExtra("your_email"));
    }
}
