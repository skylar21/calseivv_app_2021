package com.calseivv.project.service;

import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.query.DynamicLoginQuery;
import com.calseivv.project.persistence.repository.CustomLoginRepository;
import com.calseivv.project.request.LoginRequest;
import com.calseivv.project.response.LoginResponse;
import com.calseivv.project.util.PasswordEncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    CustomLoginRepository customLoginRepository;

    /**
     * Returns the user the tries to login using the credentials
     *
     * @param loginRequest Refers to the login request to be queried
     * @return
     */
    public LoginResponse userLogin(LoginRequest loginRequest) {
        DynamicLoginQuery dynamicLoginQuery = new DynamicLoginQuery();
        dynamicLoginQuery.setUsername(loginRequest.getUsername());
        dynamicLoginQuery.setPassword(PasswordEncryptorUtil.encrypt((loginRequest.getPassword())));
        List<UserEntity> userList = customLoginRepository.query(dynamicLoginQuery);

        if (userList.isEmpty()) {
            return null;
        }

        return buildResponse(userList.get(0));
    }

    /**
     * Builds the response upon logging in
     *
     * @param userEntity Refers to the user entity logged in
     * @return
     */
    private LoginResponse buildResponse(UserEntity userEntity) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserInfo(userEntity);
        return loginResponse;
    }

}
