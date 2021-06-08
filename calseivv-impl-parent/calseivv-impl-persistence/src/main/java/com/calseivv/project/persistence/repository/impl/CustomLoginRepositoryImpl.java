package com.calseivv.project.persistence.repository.impl;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.query.DynamicLoginQuery;
import com.calseivv.project.persistence.repository.CustomLoginRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("CustomLoginRepository")
public class CustomLoginRepositoryImpl implements CustomLoginRepository {

    @PersistenceContext
    private EntityManager entityManager;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public List<UserEntity> query(DynamicLoginQuery dynamicLoginQuery) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        buildCriteria(dynamicLoginQuery, criteriaBuilder, criteriaQuery, userRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private CriteriaQuery<UserEntity> buildCriteria(DynamicLoginQuery dynamicLoginQuery, CriteriaBuilder criteriaBuilder, CriteriaQuery<UserEntity> criteriaQuery, Root<UserEntity> userRoot) {
        predicates.clear();

        if (dynamicLoginQuery.getUsername() != null && dynamicLoginQuery.getPassword() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("username"), dynamicLoginQuery.getUsername()));
            predicates.add(criteriaBuilder.equal(userRoot.get("password"), dynamicLoginQuery.getPassword()));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return criteriaQuery;
    }
}
