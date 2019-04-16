package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.dto.*;
import com.app.repository.AnswerRepository;
import com.app.repository.QuizRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService {

    private QuizRepository quizRepository;
    private AnswerRepository answerRepository;
    private ModelMapperService modelMapperService;
    private UserService userService;
    private AnswerService answerService;
    private QuestionService questionService;

    public QuizService(QuizRepository quizRepository, AnswerRepository answerRepository, ModelMapperService modelMapperService, UserService userService, AnswerService answerService, QuestionService questionService) {
        this.quizRepository = quizRepository;
        this.answerRepository = answerRepository;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
        this.answerService = answerService;
        this.questionService = questionService;
    }

    public void addQuiz(QuizDto quiz) {
        if (quiz == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD QUIZ - QUIZ IS NULL");
        }
        quizRepository.add(modelMapperService.fromQuizDtoToQuiz(quiz));

    }

    public void updateQuiz(QuizDto quiz) {
        if (quiz == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE QUIZ - QUIZ IS NULL");
        }
        quizRepository.update(modelMapperService.fromQuizDtoToQuiz(quiz));
    }

    public void deleteQuiz(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE QUIZ - ID IS NULL");
        }
        quizRepository.delete(id);
    }

    public Optional<QuizDto> findOneQuiz(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE QUIZ - ID IS NULL");
        }
        return quizRepository.findById(id).map(p -> modelMapperService.fromQuizToQuizDto(p));
    }

    public List<QuizDto> findAllQuiz() {
        return quizRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromQuizToQuizDto(p))
                .collect(Collectors.toList());
    }

    public Optional<QuizDto> findNewestOneQuiz() {
        List<QuizDto> quizDtoList = findAllQuiz();
        Optional<QuizDto> newestQuiz = quizDtoList.stream().findFirst();
        for (QuizDto q : quizDtoList) {
            if (q.getDate().isAfter(newestQuiz.get().getDate())) {
                newestQuiz = Optional.of(q);
            }
        }
        return newestQuiz;
    }

    public void calculationPoints(QuestionsWithAnswersDto questionsWithAnswersDto) {
        if (questionsWithAnswersDto == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALCULATION POINTS - QUESTIONS WITH ANSWERS IS NULL");
        }
        QuizDto quizDatabase = findOneQuiz(questionsWithAnswersDto.getQuiz().getId()).get();
        UserDto userDatabase = userService.findOneUser(quizDatabase.getUser().getId()).get();
        Integer results = 0;
        if (questionsWithAnswersDto.getAnswers() != null) {
            List<QuestionDto> questions = questionsWithAnswersDto.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                if (questionsWithAnswersDto.getAnswers().get(i) != null) {
                    Long answerUser = Long.valueOf(questionsWithAnswersDto.getAnswers().get(i));
                    AnswerDto answerCorrect = answerService.findCorrectAnswer(questions.get(i).getId());
                    if (answerUser.equals(answerCorrect.getId())) {
                        results += questionService.findOneQuestion(questions.get(i).getId()).get().getLevel().getNameLevel().getPoint();
                    }
                }
            }
        }

        if (userDatabase.getBestResult() == null || userDatabase.getBestResult() < results) {
            userDatabase.setBestResult(results);
            userService.updateUser(userDatabase);
        }
        quizDatabase.setResult(results);
        updateQuiz(quizDatabase);
    }

    public List<QuizDto> findQuizOneUser(Long userId) {
        if (userId == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ALL QUIZS FOR ONE USER - USER ID IS NULL");
        }
        return findAllQuiz().stream().filter(x -> x.getUser().getId().equals(userId)).sorted((s1, s2) -> s2.getDate().compareTo(s1.getDate())).collect(Collectors.toList());
    }
}
