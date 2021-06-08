package com.calseivv.project.service;

import com.calseivv.project.persistence.model.*;
import com.calseivv.project.persistence.model.query.DynamicUserQuery;
import com.calseivv.project.persistence.repository.*;
import com.calseivv.project.request.GetUserRequest;
import com.calseivv.project.request.PasswordRequest;
import com.calseivv.project.response.GetScoreResponse;
import com.calseivv.project.response.GetUserResponse;
import com.calseivv.project.response.PasswordResponse;
import com.calseivv.project.util.PasswordEncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ScoreRepository scoreRepository;

    public GetUserResponse getUser(UUID userId) {
        Optional<UserEntity> userEntityOp = userRepository.findById(userId);
        if (userEntityOp.get() != null) {
            return buildUserResponse(userEntityOp.get());
        }
        return null;
    }

    public GetUserResponse getContent(UUID contentId) {
        Optional<ContentEntity> contentEntityOp = contentRepository.findById(contentId);
        if (contentEntityOp.get() != null) {
            return buildContentResponse(contentEntityOp.get());
        }
        return null;
    }

    public GetUserResponse getUsers(GetUserRequest getUserRequest) {
        DynamicUserQuery dynamicUserQuery = new DynamicUserQuery();
        dynamicUserQuery.setUserRole(getUserRequest.getUserRole());
        List<UserEntity> userEntityList = customUserRepository.query(dynamicUserQuery);

        return buildUserListResponse(userEntityList);
    }

    public boolean verifyExaminee(UUID userId) {
        boolean isSuccessful = false;

        Optional<UserEntity> userEntityOp = userRepository.findById(userId);
        UserEntity user;

        if (userEntityOp.get() != null) {
            user = userEntityOp.get();
            if (user.getUserRole().equals(UserRoleEnum.EX.toString())) {
                user.setVerified(true);
                userRepository.save(user);
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    public PasswordResponse getSecretQuestion(PasswordRequest passwordRequest) {
        DynamicUserQuery dynamicUserQuery = new DynamicUserQuery();
        dynamicUserQuery.setUsername(passwordRequest.getUsername());
        List<UserEntity> userEntityList = customUserRepository.query(dynamicUserQuery);

        PasswordResponse passwordResponse = new PasswordResponse();

        if (!userEntityList.isEmpty()) {
            if (userEntityList.get(0).getUserRole().equals(UserRoleEnum.EX.toString())) {
                Optional<QuestionEntity> questionEntity = questionRepository.findById(userEntityList.get(0).getSecretId());
                if (questionEntity != null) {
                    passwordResponse.setSecretQuestion(questionEntity.get().getQuestion());
                }
            }
        }
        return passwordResponse;
    }

    public void updatePassword(PasswordRequest passwordRequest) {
        if (passwordRequest.getUsername() != null) {
            DynamicUserQuery dynamicUserQuery = new DynamicUserQuery();
            dynamicUserQuery.setUsername(passwordRequest.getUsername());
            List<UserEntity> userEntityList = customUserRepository.query(dynamicUserQuery);

            if (!userEntityList.isEmpty()) {
                UserEntity userToUpdate = userEntityList.get(0);
                userToUpdate.setPassword(PasswordEncryptorUtil.encrypt(passwordRequest.getPassword()));
                userRepository.save(userToUpdate);
            }
        }
    }

    public boolean checkSecretAnswer(String username, String answer) {
        boolean isCorrect = false;
        DynamicUserQuery dynamicUserQuery = new DynamicUserQuery();
        dynamicUserQuery.setUsername(username);
        dynamicUserQuery.setSecretAnswer(PasswordEncryptorUtil.encrypt(answer));
        List<UserEntity> userEntityList = customUserRepository.query(dynamicUserQuery);

        if (!userEntityList.isEmpty()) {
            isCorrect = true;
        }

        return isCorrect;

    }

    public GetScoreResponse checkScore(String userId) {
        GetScoreResponse getScoreResponse = new GetScoreResponse();
        ScoreEntity scoreEntity = scoreRepository.findByUserId(UUID.fromString(userId));
        getScoreResponse.setScore(scoreEntity);
        return getScoreResponse;
    }

    private GetUserResponse buildUserResponse(UserEntity userEntity) {
        GetUserResponse response = new GetUserResponse();
        response.setUserInfo(userEntity);
        return response;
    }

    private GetUserResponse buildUserListResponse(List<UserEntity> userEntityList) {
        GetUserResponse response = new GetUserResponse();
        response.setUserList(userEntityList);
        return response;
    }

    private GetUserResponse buildContentResponse(ContentEntity contentEntity) {
        GetUserResponse response = new GetUserResponse();
        response.setContentInfo(contentEntity);
        return response;
    }


}
