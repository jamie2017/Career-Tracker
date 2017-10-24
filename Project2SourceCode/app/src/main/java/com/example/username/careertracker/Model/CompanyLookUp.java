package com.example.username.careertracker.Model;

import java.util.HashMap;

/**
 * Created by JMYE on 4/22/17.
 */

public class CompanyLookUp {
    private HashMap<String,String>  com2Id;
    private HashMap<String, String> id2Com;

    public CompanyLookUp() {
        com2Id = new HashMap<>();
        id2Com = new HashMap<>();
    }



    public boolean checkComByName(String cName) {
        return com2Id.containsKey(cName);
    }

    public boolean checkComById(String cId) {
        return id2Com.containsKey(cId);
    }

    public void setComLookUp(String cName, String cId) {
        if (!checkComByName(cName)) {
            this.com2Id.put(cName,cId);

        }
        if (!checkComById(cId)) {
            this.id2Com.put(cId,cName);
        }
    }

    public String getComIdByName(String cName) {
        if (checkComByName(cName)) {
            return com2Id.get(cName);
        }
        return null;
    }

    public String getComNameById(String cId) {
        if (checkComById(cId)) {
            return id2Com.get(cId);
        }
        return null;
    }
}
