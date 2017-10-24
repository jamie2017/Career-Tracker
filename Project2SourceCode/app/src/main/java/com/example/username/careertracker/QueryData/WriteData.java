package com.example.username.careertracker.QueryData;

import com.example.username.careertracker.Model.ApplicationStack;
import com.example.username.careertracker.Model.ApplicationStep;
import com.example.username.careertracker.Model.Company;
import com.example.username.careertracker.Model.Contacts;
import com.example.username.careertracker.Model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by JMYE on 4/20/17.
 */

enum  JobType {
    FullTime,
    PartTime,
    Internship,
    Contrct,
}

enum PostionType {
    Backend,
    Frontend,
    Mobile,
    FullStack,
}
public class WriteData {
    private DatabaseReference userRef;
    private FirebaseUser roofRef;
    private String uid;
    private DatabaseReference comRef;


    public WriteData(DatabaseReference userLoginRef,FirebaseUser roofRef) {
        this.roofRef = roofRef;
        if (this.roofRef != null) {
            this.uid = this.roofRef.getUid(); // current user id
        }
        this.userRef = userLoginRef.child("users").child(uid);

    }

    public void addUser(FirebaseUser user) {
        String loginUserEmail = user.getEmail();
        User newUser = new User(uid, loginUserEmail);
        userRef.setValue(newUser);
    }


    // create new applicaton/ company/contact
    public String createApplication(String comId) {
        String appId = userRef.child("applications").push().getKey();
        return appId;
    }


    public ApplicationStep addSingleApplication(String appId, String comName, String remark, String jobType, String curStep, String curStepInterviewTime, boolean important, String posType, String jobSource, String contactEmail) {


        return new ApplicationStep(appId,comName,remark,jobType,curStep,curStepInterviewTime,important,posType,jobSource,contactEmail);
    }

    public void appendNewApplicationNode(ApplicationStack appStack, String appId,ApplicationStep application) {
        String appStackId = appStack.getAppStackId();
        userRef.child("applications").child(appStackId).child(appId).setValue(application);
    }

    public ApplicationStack createNewApplicationStack(String appStackId) {
        ApplicationStack appStack = new ApplicationStack(appStackId);
        return appStack;
    }






    public String addNewCompany(String comName, String comId) {
//        String comId = userRef.child("company").push().getKey();
        Company com = new Company(comId,comName.toUpperCase());
        userRef.child("company").child(comId).setValue(com);
        return com.getComId();

    }

    public String addNewCompany(String comName) {
        String comId = userRef.child("company").push().getKey();
        Company com = new Company(comId,comName.toUpperCase());
        userRef.child("company").child(comId).setValue(com);
        return com.getComId();

    }
    public void addSingleCompany(List<String> comList) {
        userRef.child("company").setValue(comList);
    }

    public String addNewContact(String hrName,String jobTitle,String email,String comName){
        Contacts contact = new Contacts();
        if (hrName.contains(" ")) {
            String[] name = hrName.split(" ");
            contact.setFirstName(name[0]);
            contact.setLastName(name[1]);
        } else {
            contact.setFirstName(hrName);
        }
        contact.setComId(comName);
        contact.setEmail(email);
//        Log.i(TAG,">>>>>>>>>>>>>>> " +email);
//        Log.i(TAG,"！！！！！！！！！！！！ " + email.substring(0, email.indexOf('@')));
        contact.setJobTitle(jobTitle);
        String contractId = comName+" " + jobTitle+ " " + email.substring(0, email.indexOf('@'));
        contact.setContractId(contractId);
        userRef.child("contacts").child(contractId).setValue(contact);
        return contractId;

    }








            // update info for applicaton/ company/contact

    public void updateApplication() {

    }

    public void updateCompany() {

    }

    public void updateContact() {

    }


    // Delete info for applicaton/ company/contact

    public void delApplication() {

    }

    public void delCompany() {

    }

    public void delContact() {

    }


}
