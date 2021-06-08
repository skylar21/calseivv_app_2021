package com.calseivv.project.persistence.repository.impl;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.query.DynamicUserQuery;
import com.calseivv.project.persistence.repository.CustomUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("CustomUserRepository")
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public List<UserEntity> query(DynamicUserQuery dynamicUserQuery) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        buildCriteria(dynamicUserQuery, criteriaBuilder, criteriaQuery, userRoot);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private CriteriaQuery<UserEntity> buildCriteria(DynamicUserQuery dynamicUserQuery, CriteriaBuilder criteriaBuilder, CriteriaQuery<UserEntity> criteriaQuery, Root<UserEntity> userRoot) {
        predicates.clear();

        if (dynamicUserQuery.getUserRole() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("userRole"), dynamicUserQuery.getUserRole()));
        }

        if (dynamicUserQuery.getUsername() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("username"), dynamicUserQuery.getUsername()));
        }

        if (dynamicUserQuery.getSecretAnswer() != null) {
            predicates.add(criteriaBuilder.equal(userRoot.get("secretAnswer"), dynamicUserQuery.getSecretAnswer()));
        }
        
        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return criteriaQuery;
    }
}
