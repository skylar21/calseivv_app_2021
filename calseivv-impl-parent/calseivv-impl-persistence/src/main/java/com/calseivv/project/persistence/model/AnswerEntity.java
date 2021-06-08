package com.calseivv.project.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class AnswerEntity extends UUIDBasedEntity {

    private static final long serialVersionUID = 5052889271724871130L;

    @Column(name = "is_mc")
    private Boolean isMc;

    private String answer;

    public boolean isMc() {
        return isMc;
    }

    public void setMc(boolean mc) {
        isMc = mc;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
