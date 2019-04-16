package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Answer;
import com.app.model.Question;
import com.app.model.dto.AnswerDto;
import com.app.model.dto.QuestionDto;
import com.app.repository.AnswerRepository;
import com.app.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private ModelMapperService modelMapperService;
    private QuestionService questionService;


    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ModelMapperService modelMapperService, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.modelMapperService = modelMapperService;
        this.questionService = questionService;
    }

    public void addAnswer(AnswerDto answer) {
        if (answer == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD ANSWER - ANSWER IS NULL");
        }
        Answer a = modelMapperService.fromAnswerDtoToAnswer(answer);
        Question q = questionRepository.findById(answer.getQuestionId()).orElseThrow(NullPointerException::new);
        a.setQuestion(q);
        answerRepository.add(a);
    }

    public void addListAnswers(QuestionDto question, Long questionId) {
        if (question == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD ANSWERS LIST - QUESTION IS NULL");
        }
        if (question.getAnswers() == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD ANSWERS LIST - ANSWERS LIST IS NULL");
        }
        if (questionId == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD ANSWERS LIST - QUESTION ID IS NULL");
        }
        for (AnswerDto answerDto : question.getAnswers()) {
            answerDto.setQuestionId(questionId);
            addAnswer(answerDto);
        }
    }

    public void updateAnswer(AnswerDto answer) {
        if (answer == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE ANSWER - ANSWER IS NULL");
        }
        answerRepository.update(modelMapperService.fromAnswerDtoToAnswer(answer));
    }

    public void deleteAnswer(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE ANSWER - ID IS NULL");
        }
        answerRepository.delete(id);
    }

    public Optional<AnswerDto> findOneAnswer(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE ANSWER - ID IS NULL");
        }
        return answerRepository.findById(id).map(p -> modelMapperService.fromAnswerToAnswerDto(p));
    }

    public List<AnswerDto> findAllAnswer() {
        return answerRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromAnswerToAnswerDto(p))
                .collect(Collectors.toList());
    }

    public AnswerDto findCorrectAnswer(Long idQuestion) {
        if (idQuestion == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE CORRECT ANSWER - ID QUESTION IS NULL");
        }
        List<AnswerDto> answers = questionService.findOneQuestion(idQuestion).get().getAnswers();
        for (AnswerDto a : answers) {
            if (a.getCorrect()) {
                return a;
            }
        }
        return null;
    }
}
