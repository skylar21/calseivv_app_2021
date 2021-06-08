package com.calseivv.project.controller;

import com.calseivv.project.request.PasswordRequest;
import com.calseivv.project.response.PasswordResponse;
import com.calseivv.project.service.UserService;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;


@SessionScope
@Component(value = "passwordController")
@ELBeanName(value = "passwordController")
public class PasswordController {

    @Autowired
    UserService userService;
    private PasswordRequest secretQuestionRequest = new PasswordRequest();
    private PasswordRequest passwordRequest = new PasswordRequest();
    private PasswordResponse secretQuestionResponse = new PasswordResponse();
    private String secretAnswer;
    private boolean isCorrect = false;

    public void getSecretQuestion() {
        secretQuestionResponse = userService.getSecretQuestion(secretQuestionRequest);
    }

    public void updatePassword() throws IOException {
        if (isCorrect) {
            passwordRequest.setUsername(secretQuestionRequest.getUsername());
            userService.updatePassword(passwordRequest);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password Update Success!", "Please Try To Login!"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
        }
    }

    public void checkSecretAnswer() {
        isCorrect = userService.checkSecretAnswer(secretQuestionRequest.getUsername(), secretAnswer);
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("userInput") && event.getNewStep().equals("secretInput")) {
            getSecretQuestion();
            if (getSecretQuestionResponse().getSecretQuestion() != null) {
                return event.getNewStep();
            } else {
                return event.getOldStep();
            }
        }
        if (event.getOldStep().equals("secretInput") && event.getNewStep().equals("updateInput")) {
            checkSecretAnswer();
            if (isCorrect) {
                return event.getNewStep();
            } else {
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    public PasswordRequest getSecretQuestionRequest() {
        return secretQuestionRequest;
    }

    public void setSecretQuestionRequest(PasswordRequest secretQuestionRequest) {
        this.secretQuestionRequest = secretQuestionRequest;
    }

    public PasswordRequest getPasswordRequest() {
        return passwordRequest;
    }

    public void setPasswordRequest(PasswordRequest passwordRequest) {
        this.passwordRequest = passwordRequest;
    }

    public PasswordResponse getSecretQuestionResponse() {
        return secretQuestionResponse;
    }

    public void setSecretQuestionResponse(PasswordResponse secretQuestionResponse) {
        this.secretQuestionResponse = secretQuestionResponse;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}


