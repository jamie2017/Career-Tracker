package com.example.username.careertracker.Model;

/**
 * Created by JMYE on 4/20/17.
 */


enum Location {
    East,
    Mid,
    West,
}

public class Company {
    private String comId;
    private String comName;
    private String priorityStar;
    private Location location;

    public Company() {
    }

    public Company(String comId, String comName) {
        this.comId = comId;
        this.comName = comName;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getPriorityStar() {
        return priorityStar;
    }

    public void setPriorityStar(String priorityStar) {
        this.priorityStar = priorityStar;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
