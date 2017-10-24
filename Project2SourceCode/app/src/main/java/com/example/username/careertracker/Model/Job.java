package com.example.username.careertracker.Model;

/**
 * Created by JMYE on 4/20/17.
 */

//enum  JobType {
//    FullTime,
//    PartTime,
//    Internship,
//    Contrct,
//}
//
//enum PostionType {
//    Backend,
//    Frontend,
//    Mobile,
//    FullStack,
//}

public class Job {
    private String jobType;
    private String posType;
    private boolean needAtOnce;
    private String comId;
    private String jobSource; // refer or compus or job board

    public String getJobSource() {
        return jobSource;
    }

    public void setJobSource(String jobSource) {
        this.jobSource = jobSource;
    }


    public Job(String jobType, String posType, String comId) {
        this.jobType = jobType;
        this.posType = posType;
        this.comId = comId;
    }

    public Job() {
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public boolean isNeedAtOnce() {
        return needAtOnce;
    }

    public void setNeedAtOnce(boolean needAtOnce) {
        this.needAtOnce = needAtOnce;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }
}
