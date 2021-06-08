package com.calseivv.project.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "exam")
public class ExamEntity extends UUIDBasedEntity {

    private static final long serialVersionUID = 5052889271724871130L;

    private int timeLimit;

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
