package com.calseivv.project.persistence.model;

import javax.persistence.Table;

@Table(name = "question_type")
public class QuestionTypeEntity {

    private String typeAlias;
    private String typeValue;

    public String getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(String typeAlias) {
        this.typeAlias = typeAlias;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
