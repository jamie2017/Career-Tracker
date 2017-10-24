package com.example.username.careertracker.Model;

import java.text.SimpleDateFormat;
import java.util.Date;


//enum  InterviewSteps {
//    HR_Screen,
//    OA,
//    Tech,
//    HiringManager_Screen,
//    Onsite,
//    VP_Screen,
//    Offer,
//}
//
//enum StatusType {
//    Pass,
//    Fail,
//    Pending,
//}



public class ApplicationStep {
    private String appId;
    private String comName;
    private String remark;
    private String jobType;
    private String curStep;
    private String curStepInterviewTime;
    private boolean important;
    private String posType;
    private String jobSource;
    private String contactEmail;

    public ApplicationStep() {
    }

    public ApplicationStep(String appId, String comName, String jobType, String curStep, String curStepInterviewTime, boolean important, String posType, String submitType, String contactEmail) {
        this.appId = appId;
        this.comName = comName;
        this.jobType = jobType;
        this.curStep = curStep;
        this.curStepInterviewTime = curStepInterviewTime;
        this.important = important;
        this.posType = posType;
        this.jobSource = submitType;
        this.contactEmail = contactEmail;
    }

    public ApplicationStep(String appId, String comName, String remark, String jobType, String curStep, String curStepInterviewTime, boolean important, String posType, String jobSource, String contactEmail) {
        this.appId = appId;
        this.comName = comName;
        this.remark = remark;
        this.jobType = jobType;
        this.curStep = curStep;
        this.curStepInterviewTime = curStepInterviewTime;
        this.important = important;
        this.posType = posType;
        this.jobSource = jobSource;
        this.contactEmail = contactEmail;
    }

    public ApplicationStep(String comName, String jobType, String curStepInterviewTime, String posType, String submitType, String contactEmail) {
        this.comName = comName;
        this.jobType = jobType;
        this.curStepInterviewTime = curStepInterviewTime;
        this.posType = posType;
        this.jobSource = submitType;
        this.contactEmail = contactEmail;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCurStep() {
        return curStep;
    }

    public void setCurStep(String curStep) {
        this.curStep = curStep;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getSubmitType() {
        return jobSource;
    }

    public void setSubmitType(String submitType) {
        this.jobSource = submitType;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCurStepInterviewTime() {

        return curStepInterviewTime;
    }


    public void setCurStepInterviewTime(String timeString) {
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());

        this.curStepInterviewTime = myDateFormat.format(new Date(timeString));
    }


}
