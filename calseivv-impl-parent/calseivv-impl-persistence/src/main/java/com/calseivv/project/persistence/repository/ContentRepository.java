package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<ContentEntity, UUID> {
}
