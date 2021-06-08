package com.calseivv.project.persistence.model;

import javax.persistence.Table;

@Table(name = "user_role")
public class UserRoleEntity {

    private String roleAlias;
    private String roleValue;

    public String getRoleAlias() {
        return roleAlias;
    }

    public void setRoleAlias(String roleAlias) {
        this.roleAlias = roleAlias;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }
}
