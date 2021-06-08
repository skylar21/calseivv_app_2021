package com.calseivv.project.response;

import com.calseivv.project.persistence.model.ContentEntity;
import com.calseivv.project.persistence.model.UserEntity;

public class RegistrationResponse {

    UserEntity userInfo;
    ContentEntity contentInfo;
    String message;

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }

    public ContentEntity getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(ContentEntity contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
