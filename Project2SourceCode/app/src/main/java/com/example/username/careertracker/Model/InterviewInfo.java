package com.example.username.careertracker.Model;

import java.util.Date;

/**
 * Created by JMYE on 4/22/17.
 */

public class InterviewInfo {
    private Date Time;
    private String Location;
    private String priorityStar;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPriorityStar() {
        return priorityStar;
    }

    public void setPriorityStar(String priorityStar) {
        this.priorityStar = priorityStar;
    }

    public InterviewInfo(String appId, Date time) {

        this.appId = appId;
        this.Time = time;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}