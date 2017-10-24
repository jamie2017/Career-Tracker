package com.example.username.careertracker.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by JMYE on 4/20/17.
 */
@IgnoreExtraProperties
public class User {
    public String userId;
    public String userName;
    public String userEmail;
    public boolean visited;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId,String userEmail) {

        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
