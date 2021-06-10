package com.calseivv.project.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "content")
public class ContentEntity extends UUIDBasedEntity {

    private static final long serialVersionUID = 5052889271724871130L;

    @Column(name = "portrait_byte")
    private byte[] portraitByte;

    @Column(name = "identification_byte")
    private byte[] identificationByte;

    public byte[] getPortraitByte() {
        return portraitByte;
    }

    public void setPortraitByte(byte[] portraitByte) {
        this.portraitByte = portraitByte;
    }

    public byte[] getIdentificationByte() {
        return identificationByte;
    }

    public void setIdentificationByte(byte[] identificationByte) {
        this.identificationByte = identificationByte;
    }
}
