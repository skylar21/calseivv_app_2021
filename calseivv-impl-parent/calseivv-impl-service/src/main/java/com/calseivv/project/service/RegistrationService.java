package com.calseivv.project.service;

import com.calseivv.project.persistence.model.ContentEntity;
import com.calseivv.project.persistence.model.UserEntity;
import com.calseivv.project.persistence.model.UserRoleEnum;
import com.calseivv.project.persistence.repository.ContentRepository;
import com.calseivv.project.persistence.repository.UserRepository;
import com.calseivv.project.request.RegistrationRequest;
import com.calseivv.project.response.RegistrationResponse;
import com.calseivv.project.util.PasswordEncryptorUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContentRepository contentRepository;

    public RegistrationResponse register(RegistrationRequest registerUserRequest) {
        try {
            UserEntity userEntity = new UserEntity();
            ContentEntity contentEntity = new ContentEntity();

            userEntity.setFirstName(registerUserRequest.getFirstName());
            userEntity.setMiddleName(registerUserRequest.getMiddleName());
            userEntity.setLastName(registerUserRequest.getLastName());
            userEntity.setSex(registerUserRequest.getSex());
            userEntity.setBirthdate(registerUserRequest.getBirthdate());
            userEntity.setUsername(registerUserRequest.getUsername());
            userEntity.setPassword(PasswordEncryptorUtil.encrypt(registerUserRequest.getPassword()));
            userEntity.setUserRole(UserRoleEnum.EX.toString());
            userEntity.setVerified(registerUserRequest.isVerified());
            userEntity.setApplicationId(generateApplicationId());
            userEntity.setSecretAnswer(PasswordEncryptorUtil.encrypt(registerUserRequest.getSecretAnswer()));
            userEntity.setSecretId(UUID.fromString(registerUserRequest.getSecretId()));
            userEntity.setTakenExam(false);

            if (registerUserRequest.getPortraitByte() == null) {
                return buildResponseMessage("Missing Portrait Photo");
            }

            if (registerUserRequest.getIdentificationByte() == null) {
                return buildResponseMessage("Missing ID");
            }

            contentEntity.setPortraitByte(registerUserRequest.getPortraitByte());
            contentEntity.setIdentificationByte(registerUserRequest.getIdentificationByte());
            contentEntity = contentRepository.save(contentEntity);

            userEntity.setContentId(contentEntity.getId());
            userEntity = userRepository.save(userEntity);

            return buildResponse(userEntity, contentEntity);
            
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                if (e.getCause() instanceof ConstraintViolationException) {
                    String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
                    if (constraintName.contains("username")) {
                        return buildResponseMessage("Username Already Taken");
                    }
                }
            }
            return new RegistrationResponse();
        }
    }

    private BigInteger generateApplicationId() {
        BigInteger maxLimit = new BigInteger("99999999");
        BigInteger minLimit = new BigInteger("00000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger resultBigInt = new BigInteger(len, randNum);
        if (resultBigInt.compareTo(minLimit) < 0)
            resultBigInt = resultBigInt.add(minLimit);
        if (resultBigInt.compareTo(bigInteger) >= 0)
            resultBigInt = resultBigInt.mod(bigInteger).add(minLimit);

        return resultBigInt;
    }

    private RegistrationResponse buildResponse(UserEntity userEntity, ContentEntity contentEntity) {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setUserInfo(userEntity);
        registrationResponse.setContentInfo(contentEntity);
        return registrationResponse;
    }

    private RegistrationResponse buildResponseMessage(String message) {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setMessage(message);
        return registrationResponse;
    }

}
