package com.example.groupproject2;

public class user {
    String user;
    String userID;
    String email;
    String currentTime;
    String latitude;
    String longitude;

    public user(String userID, String user, String email, String currentTime, String latitude, String longitude) {
        this.userID = userID;
        this.user = user;
        this.email = email;
        this.currentTime = currentTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }
}
