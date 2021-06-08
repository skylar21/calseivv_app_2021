package com.calseivv.project.response;

import com.calseivv.project.persistence.model.ChoiceEntity;
import com.calseivv.project.persistence.model.QuestionEntity;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetQuestionResponse {

    private QuestionEntity questionInfo;
    private List<QuestionEntity> questions;
    private List<String> choices;
    private Map<UUID, String> questionChoiceMap;
    private MultiValuedMap<UUID, String> questionChoiceMultiMap;
    private List<ChoiceEntity> choiceEntities;

    public QuestionEntity getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(QuestionEntity questionInfo) {
        this.questionInfo = questionInfo;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public Map<UUID, String> getQuestionChoiceMap() {
        return questionChoiceMap;
    }

    public void setQuestionChoiceMap(Map<UUID, String> questionChoiceMap) {
        this.questionChoiceMap = questionChoiceMap;
    }

    public MultiValuedMap<UUID, String> getQuestionChoiceMultiMap() {
        return questionChoiceMultiMap;
    }

    public void setQuestionChoiceMultiMap(MultiValuedMap<UUID, String> questionChoiceMultiMap) {
        this.questionChoiceMultiMap = questionChoiceMultiMap;
    }

    public List<ChoiceEntity> getChoiceEntities() {
        return choiceEntities;
    }

    public void setChoiceEntities(List<ChoiceEntity> choiceEntities) {
        this.choiceEntities = choiceEntities;
    }
}
