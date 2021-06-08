package com.calseivv.project.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "score")
public class ScoreEntity extends UUIDBasedEntity {

    private static final long serialVersionUID = 5052889271724871130L;

    @Column(name = "user_id")
    private UUID userId;

    private int science;

    private int mathematics;

    @Column(name = "language_proficiency")
    private int languageProficiency;

    @Column(name = "reading_comprehension")
    private int readingComprehension;

    @Column(name = "overall_score")
    private int overallScore;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getMathematics() {
        return mathematics;
    }

    public void setMathematics(int mathematics) {
        this.mathematics = mathematics;
    }

    public int getLanguageProficiency() {
        return languageProficiency;
    }

    public void setLanguageProficiency(int languageProficiency) {
        this.languageProficiency = languageProficiency;
    }

    public int getReadingComprehension() {
        return readingComprehension;
    }

    public void setReadingComprehension(int readingComprehension) {
        this.readingComprehension = readingComprehension;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }
}

