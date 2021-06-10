package com.calseivv.project.controller;

import com.calseivv.project.persistence.model.ContentEntity;
import com.calseivv.project.persistence.repository.ContentRepository;
import com.calseivv.project.request.GetQuestionRequest;
import com.calseivv.project.request.RegistrationRequest;
import com.calseivv.project.response.GetQuestionResponse;
import com.calseivv.project.response.RegistrationResponse;
import com.calseivv.project.service.QuestionService;
import com.calseivv.project.service.RegistrationService;
import com.captcha.botdetect.web.jsf.JsfCaptcha;
import org.primefaces.PrimeFaces;
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
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SessionScope
@Component(value = "registrationController")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ContentRepository contentRepository;

    private JsfCaptcha captcha;
    private String captchaCode; //captcha input value from user
    private String captureFilename; //captured image filename
    private UploadedFile uploadedFile; //container for uploaded ID
    private String uploadedIdFilename; //uploaded ID file name
    private String displayFilename; //display file name for uploaded ID

    private RegistrationRequest registrationRequest = new RegistrationRequest();
    private GetQuestionResponse questionList = new GetQuestionResponse();

    private BufferedImage img;
    private ByteArrayInputStream byteArrayInputStream;

    private ByteArrayInputStream portraitPreview;

    /**
     * Reload forms after page refresh / redirection
     */
    public void loadData() throws IOException {

        portraitPreview = null;
        captureFilename = null;
        captchaCode = null;
        uploadedFile = null;
        registrationRequest = new RegistrationRequest();
        displayFilename = null;
        loadSecretQuestions();
    }

    /**
     * User registration based from the provided {@link RegistrationRequest}
     *
     * @throws IOException
     */
    public void register() throws IOException {
        RegistrationResponse response = registrationService.register(registrationRequest);
        boolean isHuman = captcha.validate(captchaCode);
        if (isHuman && response.getUserInfo() != null && response.getContentInfo() != null) {
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

        //store file
        byte[] data = file.getContent();
        
        registrationRequest.setIdentificationByte(data);

        displayFilename = uploadedFileName;
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

    public void clearCapturedPhoto() {
        portraitPreview = null;
    }

    /**
     * Capture event when taking a photo
     *
     * @param captureEvent Refers to the photo capture event
     */
    public void oncapture(CaptureEvent captureEvent) {

        byte[] data = captureEvent.getData();

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            BufferedImage bi = ImageIO.read(bais);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", os);
            portraitPreview = new ByteArrayInputStream(os.toByteArray());
            registrationRequest.setPortraitByte(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        return byteArrayInputStream;
    }

    public void setByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) {
        this.byteArrayInputStream = byteArrayInputStream;
    }

    public ByteArrayInputStream getPortraitPreview() {
        return portraitPreview;
    }

    public void setPortraitPreview(ByteArrayInputStream portraitPreview) {
        this.portraitPreview = portraitPreview;
    }
}
