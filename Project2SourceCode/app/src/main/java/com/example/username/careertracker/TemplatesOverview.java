package com.example.username.careertracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TemplatesOverview extends AppCompatActivity {
    private TextView temp1;
    private TextView temp2;
    private TextView temp3;
    private TextView temp4;
    private String LetterTitle;
    private String ThankLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates_overview);

        temp1 = (TextView) findViewById(R.id.Template_1);
        temp2 = (TextView) findViewById(R.id.Template_2);
        temp3 = (TextView) findViewById(R.id.Template_3);
        temp4 = (TextView) findViewById(R.id.Template_4);

        LetterTitle = "ThankYou Letter";

        ThankLetter = "Dear Ms. Gayle:\n" +
                "\n" +
                "Thank you for taking the time to interview me yesterday for the associate engineer position. I enjoyed meeting you and learning more about ABC Corporation.\n" +
                "\n" +
                "After speaking with you, I am confident that my skills, experience, and educational background match your needs. I was impressed with the challenging aspects of the position and am eager to learn more about the consulting work you are doing in the environmental field.\n" +
                "\n" +
                "I appreciated the opportunity to talk with you and hope you will call me at 512-234-5678 or e-mail me at student@mail.utexas.edu if you need additional information. Thank you for considering me. I am enthusiastic about the possibility of becoming an engineer with ABC Corporation. I look forward to hearing from you.\n" +
                "\n" +
                "Sincerely,\n" +
                "\n" +
                "(3 blank lines)\n" +
                "\n" +
                "Your typed name";

        temp1.setText(LetterTitle+"\n"+ThankLetter);

    }
}
