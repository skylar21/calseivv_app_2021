package com.calseivv.project.response;

import com.calseivv.project.persistence.model.ScoreEntity;

public class GetScoreResponse {

    ScoreEntity score;

    public ScoreEntity getScore() {
        return score;
    }

    public void setScore(ScoreEntity score) {
        this.score = score;
    }
}
