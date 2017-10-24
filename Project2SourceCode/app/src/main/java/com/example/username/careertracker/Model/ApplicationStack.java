package com.example.username.careertracker.Model;

import java.util.Stack;

/**
 * Created by JMYE on 4/22/17.
 */

public class ApplicationStack {
    private String appStackName;

    private String appStackId;
    private Stack<ApplicationStep> appStack;

    public ApplicationStack(String appStackId) {
        this.appStackId = appStackId;
        this.appStack = new Stack<>();
    }

    public ApplicationStack() {
    }

    public String getAppStackId() {
        return appStackId;
    }

    public void setAppStackId(String appStackId) {
        this.appStackId = appStackId;
    }

    public Stack<ApplicationStep> getAppStack() {
        return appStack;
    }

    public void addNewAppNode(ApplicationStep app) {
        this.appStack.push(app);
    }
    public String getAppStackName() {
        return appStackName;
    }

    public void setAppStackName(String appStackName) {
        this.appStackName = appStackName;
    }
}
