package com.calseivv.project.persistence.model;

public enum QuestionTypeEnum {

    MC("Multiple Choice"),
    IDN("Identification"),
    ES("Essay");

    private final String questionTypeName;

    QuestionTypeEnum(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }
}
