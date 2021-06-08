package com.calseivv.project.controller;

import com.calseivv.project.persistence.model.QuestionEntity;
import com.calseivv.project.persistence.model.UserRoleEnum;
import com.calseivv.project.request.GetQuestionRequest;
import com.calseivv.project.response.GetQuestionResponse;
import com.calseivv.project.response.GetUserResponse;
import com.calseivv.project.service.QuestionService;
import com.calseivv.project.service.UserService;
import com.calseivv.project.util.SessionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@SessionScope
@Component(value = "examController")
@ELBeanName(value = "examController")
public class ExamController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    private GetQuestionResponse questionList;
    private MultiValuedMap<UUID, String> questionChoiceMultiMap;
    private Map<UUID, String> answerMap;

    public void loadQuestions() throws IOException {

        String userRole = SessionUtils.getSession().getAttribute("role").toString();
        String userId = SessionUtils.getSession().getAttribute("userId").toString();
        GetUserResponse userResponse = userService.getUser(UUID.fromString(userId));

        if (!userRole.equals(UserRoleEnum.EX.toString()) || userResponse.getUserInfo().isTakenExam() || !userResponse.getUserInfo().isVerified()) { //redirect if not an examinee or examinee has taken the exam
            FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/home.jsf");
        }

        GetQuestionRequest getQuestionRequest = new GetQuestionRequest();
        questionList = questionService.getQuestions(getQuestionRequest);
        questionChoiceMultiMap = questionList.getQuestionChoiceMultiMap();

        //store answers for each question
        answerMap = new HashMap<>();
        for (QuestionEntity q : questionList.getQuestions()) {
            answerMap.put(q.getId(), "");
        }
    }

    public GetQuestionResponse loadSubject(String subject) {
        GetQuestionRequest getQuestionRequest = new GetQuestionRequest();
        getQuestionRequest.setSubject(subject);
        return questionService.getQuestions(getQuestionRequest);

    }

    public List<String> loadChoices(String questionId) {
        return questionChoiceMultiMap.get(UUID.fromString(questionId)).stream().collect(Collectors.toList());
    }

    public void check() throws IOException {
        String userId = SessionUtils.getSession().getAttribute("userId").toString();
        questionService.checkAnswers(answerMap, UUID.fromString(userId));
        FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/home.jsf");
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public GetQuestionResponse getQuestionList() {
        return questionList;
    }

    public void setQuestionList(GetQuestionResponse questionList) {
        this.questionList = questionList;
    }

    public MultiValuedMap<UUID, String> getQuestionChoiceMultiMap() {
        return questionChoiceMultiMap;
    }

    public void setQuestionChoiceMultiMap(MultiValuedMap<UUID, String> questionChoiceMultiMap) {
        this.questionChoiceMultiMap = questionChoiceMultiMap;
    }

    public Map<UUID, String> getSampleMap() {
        return answerMap;
    }

    public void setSampleMap(Map<UUID, String> sampleMap) {
        this.answerMap = sampleMap;
    }
}
