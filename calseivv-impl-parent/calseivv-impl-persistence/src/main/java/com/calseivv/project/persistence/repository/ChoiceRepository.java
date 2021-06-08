package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChoiceRepository extends JpaRepository<ChoiceEntity, UUID> {
}
