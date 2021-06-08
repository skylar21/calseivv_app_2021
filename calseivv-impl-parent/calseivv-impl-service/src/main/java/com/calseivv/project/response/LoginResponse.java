package com.calseivv.project.response;

import com.calseivv.project.persistence.model.UserEntity;

public class LoginResponse {

    UserEntity userInfo;

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }
}
