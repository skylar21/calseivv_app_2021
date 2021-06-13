package com.calseivv.project.controller;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.UserRoleEnum;
import com.calseivv.project.request.GetUserRequest;
import com.calseivv.project.response.GetContentResponse;
import com.calseivv.project.response.GetScoreResponse;
import com.calseivv.project.response.GetUserResponse;
import com.calseivv.project.service.ContentService;
import com.calseivv.project.service.UserService;
import com.calseivv.project.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SessionScope
@Component(value = "homeController")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    ContentService contentService;

    private GetUserRequest userRequest = new GetUserRequest();
    private GetUserResponse userResponse = new GetUserResponse();
    private GetUserResponse userResponseList = new GetUserResponse();
    private GetUserResponse userView = new GetUserResponse();
    private GetContentResponse contentView = new GetContentResponse();
    private GetScoreResponse scoreResponse = new GetScoreResponse();
    private GetScoreResponse scoreViewResponse = new GetScoreResponse();
    private GetContentResponse contentResponse = new GetContentResponse();
    private GetContentResponse exContentResponse = new GetContentResponse();

    private boolean displayUsers = false;
    private boolean displayExam = false;
    private boolean displayScore = false;
    private boolean userVerified = false;
    private String displayRole;

    /**
     * Display user details upon login
     */
    public void display() {

        String userId = SessionUtils.getSession().getAttribute("userId").toString();

        userRequest.setUserId(userId);
        GetUserResponse response = userService.getUser(UUID.fromString(userRequest.getUserId()));
        userResponse.setUserInfo(response.getUserInfo());

        contentResponse = contentService.getImage(userResponse.getUserInfo().getContentId());

        displayRole = UserRoleEnum.valueOf(userResponse.getUserInfo().getUserRole()).getUserRoleName();
        userVerified = userResponse.getUserInfo().isVerified();

        //display user list UI for TEACHERS
        if (displayRole.equals(UserRoleEnum.EX.getUserRoleName()) || displayRole.equals(UserRoleEnum.AD.getUserRoleName())) {
            displayUsers = false;
        } else {
            displayUsers = true;
        }

        //display exam info for EXAMINEES
        if (displayRole.equals(UserRoleEnum.EX.getUserRoleName())) {
            displayExam = true;
        } else {
            displayExam = false;
        }

        if (displayRole.equals(UserRoleEnum.EX.getUserRoleName()) && response.getUserInfo().isTakenExam()) {
            displayScore = true;
            scoreResponse = userService.checkScore(userId);
        }

        //display content info
        if (response.getUserInfo().getContentId() != null) {
            GetUserResponse contentResponse = userService.getContent(response.getUserInfo().getContentId());
            userResponse.setContentInfo(contentResponse.getContentInfo());
        }

        //display user list for TEACHERS
        if (displayRole.equals(UserRoleEnum.TC.getUserRoleName()) && userResponseList.getUserList() == null) {
            GetUserRequest tcRequest = new GetUserRequest();
            tcRequest.setUserRole(UserRoleEnum.EX.toString());
            userResponseList = userService.getUsers(tcRequest);
        }
    }

    /**
     * Verifies a user with a role Examinee (EX) for that user to be able to take the exam
     *
     * @param userId Refers to the user ID of the user to be verified
     */
    public void verify(String userId) {
        userService.verifyExaminee(UUID.fromString(userId));
        reloadList();
    }

    /**
     * User with a role of Teacher (TC) will be able to view all the Examinees' profile for verification
     *
     * @param userId Refers to the user ID of the user to be viewed
     */
    public void viewUserInfo(String userId) {
        GetUserResponse userResponse = userService.getUser(UUID.fromString(userId));

        if (userResponse != null) {
            UserEntity user = userResponse.getUserInfo();
            if (user != null) {
                if (user.getContentId() != null) {
                    GetUserResponse contentResponse = userService.getContent(user.getContentId());
                    GetContentResponse contentViewResponse = contentService.getImage(user.getContentId());
                    userResponse.setContentInfo(contentResponse.getContentInfo());
                    contentView = contentViewResponse;
                }
                scoreViewResponse = userService.checkScore(userId);
                userView = userResponse;
            }
        }
    }

    private void reloadList() {
        userResponseList = new GetUserResponse();
        if (displayRole.equals(UserRoleEnum.TC.getUserRoleName())) {
            GetUserRequest tcRequest = new GetUserRequest();
            tcRequest.setUserRole(UserRoleEnum.EX.toString());
            userResponseList = userService.getUsers(tcRequest);
        }
    }

    public void takeExam() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/exam.jsf");
    }

    public GetUserRequest getUserRequest() {
        return userRequest;
    }

    public GetUserResponse getUserResponse() {
        return userResponse;
    }

    public GetUserResponse getUserResponseList() {
        return userResponseList;
    }

    public GetScoreResponse getScoreResponse() {
        return scoreResponse;
    }

    public void setScoreResponse(GetScoreResponse scoreResponse) {
        this.scoreResponse = scoreResponse;
    }

    public GetUserResponse getUserView() {
        return userView;
    }

    public GetContentResponse getContentView() {
        return contentView;
    }

    public String getDisplayRole() {
        return displayRole;
    }

    public void setDisplayRole(String displayRole) {
        this.displayRole = displayRole;
    }

    public boolean isDisplayUsers() {
        return displayUsers;
    }

    public void setDisplayUsers(boolean displayUsers) {
        this.displayUsers = displayUsers;
    }

    public boolean isDisplayExam() {
        return displayExam;
    }

    public void setDisplayExam(boolean displayExam) {
        this.displayExam = displayExam;
    }

    public boolean isDisplayScore() {
        return displayScore;
    }

    public void setDisplayScore(boolean displayScore) {
        this.displayScore = displayScore;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public GetContentResponse getContentResponse() {
        return contentResponse;
    }

    public void setContentResponse(GetContentResponse contentResponse) {
        this.contentResponse = contentResponse;
    }

    public GetContentResponse getExContentResponse() {
        return exContentResponse;
    }

    public void setExContentResponse(GetContentResponse exContentResponse) {
        this.exContentResponse = exContentResponse;
    }

    public GetScoreResponse getScoreViewResponse() {
        return scoreViewResponse;
    }

    public void setScoreViewResponse(GetScoreResponse scoreViewResponse) {
        this.scoreViewResponse = scoreViewResponse;
    }
}
