package com.calseivv.project.request;

import java.math.BigInteger;
import java.util.Date;

public class RegistrationRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String sex = "M";
    private Date birthdate;
    private String username;
    private String password;
    private String userRole;
    private boolean isVerified = false;
    private BigInteger applicationId;
    private String portraitFile;
    private String idFile;
    private String secretAnswer;
    private String secretId;
    private boolean takenExam = false;
    private byte[] portraitByte;
    private byte[] identificationByte;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public BigInteger getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(BigInteger applicationId) {
        this.applicationId = applicationId;
    }

    public String getPortraitFile() {
        return portraitFile;
    }

    public void setPortraitFile(String portraitFile) {
        this.portraitFile = portraitFile;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public boolean isTakenExam() {
        return takenExam;
    }

    public void setTakenExam(boolean takenExam) {
        this.takenExam = takenExam;
    }

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
