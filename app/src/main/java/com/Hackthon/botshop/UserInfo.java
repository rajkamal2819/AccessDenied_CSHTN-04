package com.Hackthon.botshop;

import android.widget.ImageView;

public class UserInfo {

    private String phoneNum;
    private String description;
    private String emailId;
    private ImageView profilePhoto;
    private String name;

    public UserInfo(String phoneNum,String description,String emailId,String name,ImageView profilePhoto){
        this.description = description;
        this.name = name;
        this.emailId = emailId;
        this.phoneNum = phoneNum;
        this.profilePhoto = profilePhoto;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public ImageView getProfilePhoto() {
        return profilePhoto;
    }

    public String getDescription() {
        return description;
    }
}
