package com.calseivv.project.response;

import com.calseivv.project.persistence.model.ContentEntity;
import com.calseivv.project.persistence.model.UserEntity;

import java.util.List;

public class GetUserResponse {

    UserEntity userInfo;
    ContentEntity contentInfo;
    List<UserEntity> userList;

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

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }
}
