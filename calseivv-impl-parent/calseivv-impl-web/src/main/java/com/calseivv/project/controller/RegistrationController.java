package com.calseivv.project.controller;

import com.calseivv.project.request.GetQuestionRequest;
import com.calseivv.project.request.RegistrationRequest;
import com.calseivv.project.response.GetQuestionResponse;
import com.calseivv.project.response.RegistrationResponse;
import com.calseivv.project.service.QuestionService;
import com.calseivv.project.service.RegistrationService;
import com.captcha.botdetect.web.jsf.JsfCaptcha;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SessionScope
@Component(value = "registrationController")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    QuestionService questionService;

    private JsfCaptcha captcha;
    private String captchaCode; //captcha input value from user
    private String captureFilename; //captured image filename
    private UploadedFile uploadedFile; //container for uploaded ID
    private String uploadedIdFilename; //uploaded ID file name
    private String displayFilename; //display file name for uploaded ID

    private RegistrationRequest registrationRequest = new RegistrationRequest();
    private GetQuestionResponse questionList = new GetQuestionResponse();

    /**
     * Reload forms after page refresh / redirection
     */
    public void loadData() {
        deletePhoto();
        captureFilename = null;
        captchaCode = null;
        uploadedFile = null;
        uploadedIdFilename = null;
        registrationRequest = new RegistrationRequest();
        loadSecretQuestions();
    }

    /**
     * User registration based from the provided {@link RegistrationRequest}
     *
     * @throws IOException
     */
    public void register() throws IOException {
        registrationRequest.setPortraitFile(getCaptureFilename());
        registrationRequest.setIdFile(uploadedIdFilename);
        RegistrationResponse response = registrationService.register(registrationRequest);
        boolean isHuman = captcha.validate(captchaCode);
        if (isHuman && uploadedFile != null && response.getUserInfo() != null && response.getContentInfo() != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Success!", "Please Try To Login!"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failure!", response.getMessage()));
        }

    }

    /**
     * Upload ID for user verification
     *
     * @param event Refers to the file upload event from the frontend
     */
    public void uploadIdentification(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        String uploadedFileName = file.getFileName();
        String filenamePath = getRandomImageName() + "_" + uploadedFileName;

        //store file
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "resources/demo" +
                File.separator + "ids" + File.separator + filenamePath;

        byte[] data = file.getContent();
        FileImageOutputStream imageOutput;

        try {
            if (uploadedIdFilename != null) {
                String checkFilename = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "resources/demo" +
                        File.separator + "ids" + File.separator + uploadedIdFilename;
                File checkFile = new File(checkFilename);
                checkFile.delete();
            }
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in uploaded image.", e);
        }
        uploadedFile = file;
        uploadedIdFilename = filenamePath;
        displayFilename = uploadedFile.getFileName();
    }

    /**
     * Generates a random number to be used as an image name for the captured photo
     *
     * @return A random image name
     */
    private String getRandomImageName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String dateString = formatter.format(new Date());

        return "img_" + dateString;
    }

    /**
     * Capture event when taking a photo
     *
     * @param captureEvent Refers to the photo capture event
     */
    public void oncapture(CaptureEvent captureEvent) {
        if (captureFilename != null) {
            deletePhoto();
        }
        captureFilename = getRandomImageName();
        byte[] data = captureEvent.getData();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "resources/demo" +
                File.separator + "resources/images" + File.separator + captureFilename + ".jpeg";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }

    /**
     * Delete captured photo if captured a new one
     */
    private void deletePhoto() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String toDelete = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "resources/demo" +
                File.separator + "resources/images" + File.separator + captureFilename + ".jpeg";

        File file = new File(toDelete);
        file.delete();
    }

    /**
     * Loads the secret questions
     */
    private void loadSecretQuestions() {
        GetQuestionRequest getQuestionRequest = new GetQuestionRequest();
        getQuestionRequest.setQuestionType("SQ");
        questionList = questionService.getQuestions(getQuestionRequest);
    }

    public String getCaptureFilename() {
        return captureFilename;
    }

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public GetQuestionResponse getQuestionList() {
        return questionList;
    }

    public JsfCaptcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(JsfCaptcha captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getUploadedIdFilename() {
        return uploadedIdFilename;
    }

    public void setUploadedIdFilename(String uploadedIdFilename) {
        this.uploadedIdFilename = uploadedIdFilename;
    }

    public String getDisplayFilename() {
        return displayFilename;
    }

    public void setDisplayFilename(String displayFilename) {
        this.displayFilename = displayFilename;
    }
}
