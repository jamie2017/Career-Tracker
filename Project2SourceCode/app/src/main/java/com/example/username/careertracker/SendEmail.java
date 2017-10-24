package com.example.username.careertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Scroller;

import com.example.username.careertracker.Adapter.PassUserConfirm;
import com.example.username.careertracker.Adapter.SendMailAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SendEmail extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference passWordRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/password_manager/";
    private String uid;


    public static String sender = "";
    private String recipient;
    public  static String pass = "";
    private EditText editTextEmail;
    private EditText editTextRecipient;
    private EditText editTextSubject;
    private EditText editTextContent;
    private EditText editTextPass;

    private Button buttonSend;
    private ImageButton buttonScanSave;
    private ImageButton tempBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        sender = user.getEmail();
        uid = user.getUid();
        passWordRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        getExistPass();
        Intent curIntent = getIntent();
        recipient = curIntent.getStringExtra("receiver_email");
//        Toast.makeText(getApplicationContext(), recipient , Toast.LENGTH_LONG).show();
        final EditText etRmark = (EditText) findViewById(R.id.email_content);

        etRmark.setScroller(new Scroller(getApplicationContext()));
        etRmark.setVerticalScrollBarEnabled(true);
        etRmark.setMinLines(15);
        etRmark.setMaxLines(16);

        editTextEmail = (EditText) findViewById(R.id.email_address);
        editTextEmail.setText(sender);

        editTextRecipient = (EditText) findViewById(R.id.email_recipient);
        if (recipient != null) {
            editTextRecipient.setText(recipient);
        }
        editTextSubject = (EditText) findViewById(R.id.email_title);
        editTextContent = (EditText) findViewById(R.id.email_content);
        buttonSend = (Button) findViewById(R.id.send_email);
        buttonScanSave = (ImageButton) findViewById(R.id.scan_save);
        tempBtn = (ImageButton) findViewById(R.id.templates_choice);
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(SendEmail.this, TemplatesChoices.class);
                nextIntent.putExtra("your_email",sender);
                startActivity(nextIntent);
//                startActivity(new Intent(SendEmail.this, TemplatesChoices.class));
            }
        });
        buttonScanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(SendEmail.this, ScanCard.class);
                nextIntent.putExtra("next_jump","sendEmail");
                startActivity(nextIntent);
//                startActivity(new Intent(SendEmail.this, ScanCard.class));
            }
        });

        buttonSend.setOnClickListener(this);
    }

    private void sendEmail(){
//        String email = editTextEmail.getText().toString().trim();
        String typedEmail = trimString(editTextRecipient.getText().toString());
        if (recipient == null || !recipient.equals(typedEmail)) {
            recipient = typedEmail;
        }
        String subject = trimString(editTextSubject.getText().toString());
        String content = trimString(editTextContent.getText().toString());

        SendMailAdapter sm = new SendMailAdapter(this, recipient, subject, content);
        sm.execute();
    }

    private void passDialog(){
        Context context = buttonSend.getContext();
//        editTextPass = (EditText) findViewById(R.id.reenter_pass);
        new PassUserConfirm.Builder(context)
                .setTitle("Confirm send email")
                .setInputDefaultText("")
                .setInputMaxWords(20)
                .setInputHint("password")
                .setPositiveButton("Confirm", new PassUserConfirm.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
//                        pass = editTextPass.getText().toString().trim();
                        pass = (String) inputText;
                        sendEmail();
                        SaveUserEmailPasswordToDB();
                    }
                })
                .setNegativeButton("Cancel", new PassUserConfirm.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
                    }
                })
                .setOnCancelListener(new PassUserConfirm.OnCancelListener() {
                    @Override
                    public void onCancel(CharSequence inputText) {
                        // TODO
                    }
                })
                .interceptButtonAction(new PassUserConfirm.ButtonActionIntercepter() { // 拦截按钮行为
                    @Override
                    public boolean onInterceptButtonAction(int whichButton, CharSequence inputText) {
                        if ("".equals(inputText) && whichButton == DialogInterface.BUTTON_POSITIVE) {
                            // TODO 此文件夹已存在，在此做相应的提示处理
                            // 以及return true拦截此按钮默认行为
                            return true;
                        }
                        return false;
                    }
                })
                .show();

    }

    @Override
    public void onClick(View v) {
        if (!pass.equals("")) {
            sendEmail();
        } else {
            passDialog();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do your process here
            startActivity(new Intent(SendEmail.this, MainActivity.class));

        }
        return super.onKeyDown(keyCode, event);
    }


    private void SaveUserEmailPasswordToDB() {
        Map<String,String> psMap = new HashMap<String, String>(){{put("email",sender);put("pw",pass);}};
        String eKey = passWordRef.child(uid).push().getKey();
        passWordRef.child(uid).child(eKey).setValue(psMap);

    }

    private void getExistPass() {
        DatabaseReference targetRef = passWordRef.child(uid);
        targetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    Map<String, String> c = (Map)childKey.getValue();
                    if (c.get("email").equals(sender)) {
                        pass = c.get("pw").toString();
//                        Toast.makeText(getApplicationContext(), pass , Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String trimString(String input) {
        if (input != null || input != "") {
            return input.trim();
        } else {
            return input;
        }
    }
}
