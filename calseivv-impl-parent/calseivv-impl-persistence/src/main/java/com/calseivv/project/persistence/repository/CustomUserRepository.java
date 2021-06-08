package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.query.DynamicUserQuery;

import java.util.List;

public interface CustomUserRepository {

    List<UserEntity> query(DynamicUserQuery dynamicUserQuery);

}
