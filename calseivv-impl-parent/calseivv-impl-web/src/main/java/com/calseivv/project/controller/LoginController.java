package com.calseivv.project.controller;

import com.calseivv.project.request.LoginRequest;
import com.calseivv.project.response.LoginResponse;
import com.calseivv.project.service.LoginService;
import com.calseivv.project.util.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

@SessionScope
@Component(value = "loginController")
@ELBeanName(value = "loginController")
@Join(path = "/", to = "/login.jsf")
public class LoginController implements Serializable {

    @Autowired
    LoginService loginService;

    private LoginRequest loginRequest = new LoginRequest();
    private LoginResponse loginResponse = new LoginResponse();

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    /**
     * Method to login the user provided the credentials
     *
     * @throws IOException
     */
    public void userLogin() throws IOException {

        loginResponse = loginService.userLogin(loginRequest);

        boolean hasAccess = false;

        if (loginResponse != null) {

            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", loginResponse.getUserInfo().getUsername());
            session.setAttribute("userId", loginResponse.getUserInfo().getId());
            session.setAttribute("applicationId", loginResponse.getUserInfo().getApplicationId());
            session.setAttribute("role", loginResponse.getUserInfo().getUserRole());
            hasAccess = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
        }

        if (hasAccess) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/home.jsf");
        }

    }

    /**
     * Logs out the user
     *
     * @throws IOException
     */
    public void userLogout() throws IOException {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
    }

}
