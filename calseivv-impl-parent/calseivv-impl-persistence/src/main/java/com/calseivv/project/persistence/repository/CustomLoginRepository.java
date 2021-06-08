package com.calseivv.project.persistence.repository;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.query.DynamicLoginQuery;

import java.util.List;

public interface CustomLoginRepository {

    List<UserEntity> query(DynamicLoginQuery dynamicLoginQuery);

}
