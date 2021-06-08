package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.QuestionEntity;
import com.calseivv.project.persistence.model.query.DynamicQuestionQuery;

import java.util.List;

public interface CustomQuestionRepository {

    List<QuestionEntity> query(DynamicQuestionQuery dynamicQuestionQuery);

}
