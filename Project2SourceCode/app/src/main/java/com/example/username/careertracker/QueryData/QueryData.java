package com.example.username.careertracker.QueryData;

import com.example.username.careertracker.Model.ApplicationStack;
import com.example.username.careertracker.Model.Company;
import com.example.username.careertracker.Model.Contacts;
import com.example.username.careertracker.Model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by JMYE on 4/20/17.
 */

public class QueryData {

    public DatabaseReference userRef;
    private FirebaseUser roofRef;
    private String uid;
    private String comName;
    private String comId;
    private ApplicationStack curAppStack;




    public QueryData(DatabaseReference userLoginRef,FirebaseUser roofRef) {
        this.roofRef = roofRef;
        if (this.roofRef != null) {
            this.uid = this.roofRef.getUid(); // current user id
        }
        this.userRef = userLoginRef.child("users").child(uid);

    }


    public void getAndUpdateUserProfile() {
        final DatabaseReference curRef = userRef;
        curRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    return;
                }
                for (DataSnapshot childKey : dataSnapshot.getChildren()) {
                    User u = childKey.getValue(User.class);
                    if (u.getUserId().equals(uid)) {
                        u.setVisited(true);
                        curRef.child(uid).setValue(u);
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public String getCompany(String cName) {
        comName = cName;
        comId = null;
        DatabaseReference targetComRef = userRef.child("company");
        targetComRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    Company  c = childKey.getValue(Company.class);
                    if (c.getComName().toUpperCase().equals(comName.toUpperCase())) {
                        comId = c.getComId();
                        return ;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return comId;

    }








    public ApplicationStack existApplicationStack(final String stackId) {
        final String[] targetStackId = {stackId};
        DatabaseReference targetStack = userRef.child("applications");
        targetStack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    return ;
                }
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    ApplicationStack  aStk = childKey.getValue(ApplicationStack.class);
                    if (aStk.getAppStackId() == targetStackId[0]) {
                        curAppStack = aStk;
                        return ;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return curAppStack;
    }





    public String getContact(String jobTitle,String email,String comId){
        String contractId = userRef.child("contacts").push().getKey();
        Contacts contact = new Contacts(contractId,email,jobTitle,comId);
        userRef.child("contacts").child(contractId).setValue(contact);
//        System.out.println("New company is created!" + " " + email);
        return contractId;

    }











}
