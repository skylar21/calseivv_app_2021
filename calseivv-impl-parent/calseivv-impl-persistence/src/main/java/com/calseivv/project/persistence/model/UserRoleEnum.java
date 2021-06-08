package com.calseivv.project.persistence.model;

public enum UserRoleEnum {

    AD("Administrator"),
    TC("Teacher"),
    EX("Examinee");

    private final String userRoleName;

    UserRoleEnum(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }
}
