package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, UUID> {

    ScoreEntity findByUserId(UUID userId);

}
