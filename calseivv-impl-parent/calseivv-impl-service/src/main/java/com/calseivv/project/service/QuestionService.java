package com.calseivv.project.service;

import com.calseivv.project.persistence.model.*;
import com.calseivv.project.persistence.model.query.DynamicQuestionQuery;
import com.calseivv.project.persistence.repository.*;
import com.calseivv.project.request.GetQuestionRequest;
import com.calseivv.project.response.GetQuestionResponse;
import com.calseivv.project.response.GetScoreResponse;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CustomQuestionRepository customQuestionRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    UserRepository userRepository;

    public GetQuestionResponse getQuestions(GetQuestionRequest getQuestionRequest) {
        DynamicQuestionQuery dynamicQuestionQuery = new DynamicQuestionQuery();
        dynamicQuestionQuery.setQuestion(getQuestionRequest.getQuestion());
        dynamicQuestionQuery.setQuestionType(getQuestionRequest.getQuestionType());
        dynamicQuestionQuery.setSubject(getQuestionRequest.getSubject());
        List<QuestionEntity> questionEntityList = customQuestionRepository.query(dynamicQuestionQuery);
        List<String> choiceList = new ArrayList<>();
        List<ChoiceEntity> choiceEntities = new ArrayList<>();
        Map<UUID, String> questionChoiceMap = new HashMap<>();
        MultiValuedMap<UUID, String> questionChoiceMultiMap = new ArrayListValuedHashMap<>();

        if (questionEntityList != null) {
            for (QuestionEntity q : questionEntityList) {
                if (q.getQuestionType().equals(QuestionTypeEnum.MC.toString())) {
                    Optional<ChoiceEntity> choiceOp = choiceRepository.findById(q.getChoiceId());
                    if (choiceOp != null) {
                        ChoiceEntity choice = choiceOp.get();
                        questionChoiceMultiMap.put(q.getId(), choice.getValue1());
                        questionChoiceMultiMap.put(q.getId(), choice.getValue2());
                        questionChoiceMultiMap.put(q.getId(), choice.getValue3());
                        questionChoiceMultiMap.put(q.getId(), choice.getValue4());
                        questionChoiceMultiMap.put(q.getId(), choice.getValue5());
                    }
                }
            }
        }

        return buildQuestionListResponse(questionEntityList, questionChoiceMultiMap);

    }

    private GetQuestionResponse buildQuestionListResponse(List<QuestionEntity> questionEntityList, MultiValuedMap<UUID, String> questionChoiceMultiMap) {
        GetQuestionResponse getQuestionResponse = new GetQuestionResponse();
        getQuestionResponse.setQuestions(questionEntityList);
        getQuestionResponse.setQuestionChoiceMultiMap(questionChoiceMultiMap);
        return getQuestionResponse;
    }

    public void checkAnswers(Map<UUID, String> answerMap, UUID userId) {
        int overallScore = 0;
        int scienceScore = 0;
        int mathScore = 0;
        int languageProficiencyScore = 0;
        int readingComprehensionScore = 0;

        List<String> scienceSubjectList = new ArrayList<>();
        scienceSubjectList.add("BIOLOGY");
        scienceSubjectList.add("CHEMISTRY");
        scienceSubjectList.add("PHYSICS");
        scienceSubjectList.add("GENERAL SCIENCE");

        DynamicQuestionQuery questionQuery = new DynamicQuestionQuery();
        questionQuery.setQuestionType(null);
        List<QuestionEntity> allQuestions = customQuestionRepository.query(questionQuery);
        for (QuestionEntity qe : allQuestions) {
            Optional<AnswerEntity> answerOp = answerRepository.findById(qe.getAnswerId());
            if (answerOp != null) {
                AnswerEntity answerEntity = answerOp.get();
                String userAnswer = answerMap.get(qe.getId());
                if (answerEntity.isMc()) {
                    if (userAnswer.equals(answerEntity.getAnswer())) {
                        if (scienceSubjectList.contains(qe.getSubject())) {
                            scienceScore++;
                        } else if (qe.getSubject().equals("MATHEMATICS")) {
                            mathScore++;
                        } else if (qe.getSubject().contains("LANGUAGE")) {
                            languageProficiencyScore++;
                        } else if (qe.getSubject().contains("READING")) {
                            readingComprehensionScore++;
                        }
                    }
                } else {
                    if (answerEntity.getAnswer().contains(",")) {
                        String[] multipleAnswers = answerEntity.getAnswer().split(",");
                        int multipleAnswerSize = multipleAnswers.length;
                        int correctAnswerCtr = 0;
                        for (String indivAnswer : multipleAnswers) {
                            if (userAnswer.equalsIgnoreCase(indivAnswer) || indivAnswer.toLowerCase().contains(userAnswer.toLowerCase())) {
                                correctAnswerCtr++;
                            }
                        }
                        if (correctAnswerCtr == multipleAnswerSize) {
                            if (scienceSubjectList.contains(qe.getSubject())) {
                                scienceScore++;
                            } else if (qe.getSubject().equals("MATHEMATICS")) {
                                mathScore++;
                            } else if (qe.getSubject().contains("LANGUAGE")) {
                                languageProficiencyScore++;
                            } else if (qe.getSubject().contains("READING")) {
                                readingComprehensionScore++;
                            }
                        }
                    } else {
                        if (userAnswer.equalsIgnoreCase(answerEntity.getAnswer())) {
                            if (scienceSubjectList.contains(qe.getSubject())) {
                                scienceScore++;
                            } else if (qe.getSubject().equals("MATHEMATICS")) {
                                mathScore++;
                            } else if (qe.getSubject().contains("LANGUAGE")) {
                                languageProficiencyScore++;
                            } else if (qe.getSubject().contains("READING")) {
                                readingComprehensionScore++;
                            }
                        }
                    }
                }
            }
        }

        overallScore = scienceScore + mathScore + languageProficiencyScore + readingComprehensionScore;

        Optional<UserEntity> userOp = userRepository.findById(userId);
        if (userOp != null) {
            UserEntity user = userOp.get();
            ScoreEntity userScore = new ScoreEntity();
            userScore.setUserId(user.getId());
            userScore.setScience(scienceScore);
            userScore.setMathematics(mathScore);
            userScore.setLanguageProficiency(languageProficiencyScore);
            userScore.setReadingComprehension(readingComprehensionScore);
            userScore.setOverallScore(overallScore);
            user.setTakenExam(true);
            scoreRepository.save(userScore);
            userRepository.save(user);
        }
    }

}
