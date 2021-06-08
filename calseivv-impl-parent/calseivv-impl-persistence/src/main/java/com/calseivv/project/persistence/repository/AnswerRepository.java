package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.AnswerEntity;
import com.calseivv.project.persistence.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
}
