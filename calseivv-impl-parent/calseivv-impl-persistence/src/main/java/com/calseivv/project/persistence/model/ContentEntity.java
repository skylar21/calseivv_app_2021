package com.calseivv.project.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "content")
public class ContentEntity extends UUIDBasedEntity {

    private static final long serialVersionUID = 5052889271724871130L;

    private String identification;
    private String portrait;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
