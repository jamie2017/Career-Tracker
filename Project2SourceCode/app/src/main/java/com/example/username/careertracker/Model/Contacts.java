package com.example.username.careertracker.Model;

/**
 * Created by JMYE on 4/20/17.
 */



public class Contacts {
    private String contractId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String jobTitle;
    private String comId;

    public Contacts(String contractId, String email, String jobTitle, String comId) {
        this.contractId = contractId;
        this.email = email;
        this.jobTitle = jobTitle;
        this.comId = comId;
    }

    public Contacts() {
    }

    public Contacts(String firstName, String lastName, String email, String jobTitle, String comId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.comId = comId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }
}
