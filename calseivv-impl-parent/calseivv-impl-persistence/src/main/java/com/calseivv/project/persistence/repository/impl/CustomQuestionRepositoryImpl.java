package com.calseivv.project.persistence.repository.impl;

import com.calseivv.project.persistence.model.QuestionEntity;
import com.calseivv.project.persistence.model.query.DynamicQuestionQuery;
import com.calseivv.project.persistence.repository.CustomQuestionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("CustomQuestionRepository")
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public List<QuestionEntity> query(DynamicQuestionQuery dynamicQuestionQuery) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionEntity.class);
        Root<QuestionEntity> userRoot = criteriaQuery.from(QuestionEntity.class);
        buildCriteria(dynamicQuestionQuery, criteriaBuilder, criteriaQuery, userRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private CriteriaQuery<QuestionEntity> buildCriteria(DynamicQuestionQuery dynamicQuestionQuery, CriteriaBuilder criteriaBuilder, CriteriaQuery<QuestionEntity> criteriaQuery, Root<QuestionEntity> userRoot) {
        predicates.clear();

        if (dynamicQuestionQuery.getQuestion() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("question"), dynamicQuestionQuery.getQuestion()));
        }

        if (dynamicQuestionQuery.getQuestionType() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("questionType"), dynamicQuestionQuery.getQuestionType()));
        } else {
            predicates.add(criteriaBuilder.notEqual(userRoot.get("questionType"), "SQ")); //special case for secret questions
        }

        if (dynamicQuestionQuery.getSubject() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("subject"), dynamicQuestionQuery.getSubject()));
        }

//        if (dynamicQuestionQuery.getAnswerId() != null) {
//            predicates.add(criteriaBuilder.equal(userRoot.get("answer_id"), dynamicQuestionQuery.getAnswerId()));
//        }
//
//        if (dynamicQuestionQuery.getChoiceId() != null) {
//            predicates.add(criteriaBuilder.equal(userRoot.get("choice_id"), dynamicQuestionQuery.getChoiceId()));
//        }
//
//        if (dynamicQuestionQuery.getExamId() != null) {
//            predicates.add(criteriaBuilder.equal(userRoot.get("exam_id"), dynamicQuestionQuery.getExamId()));
//        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return criteriaQuery;
    }
}
