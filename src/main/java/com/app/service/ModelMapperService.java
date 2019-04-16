package com.app.service;

import com.app.model.*;
import com.app.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModelMapperService {

    public CategoryDto fromCategoryToCategoryDto(Category category) {
        return category == null ? null : CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return categoryDto == null ? null : Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .questions(new HashSet<>())
                .build();
    }

    public AnswerDto fromAnswerToAnswerDto(Answer answer) {
        return answer == null ? null : AnswerDto.builder()
                .id(answer.getId())
                .correct(answer.isCorrect())
                .text(answer.getText())
                .questionId(answer.getQuestion().getId())
                .build();
    }

    public Answer fromAnswerDtoToAnswer(AnswerDto answerDto) {
        return answerDto == null ? null : Answer.builder()
                .id(answerDto.getId())
                .correct(answerDto.getCorrect())
                .text(answerDto.getText())
                .question(Question.builder().id(answerDto.getQuestionId()).build())
                .build();
    }

    private int positionOfGoodAnswer(Set<Answer> answers) {
        if (answers == null) {
            return -1;
        }

        List<Answer> answers2 = new ArrayList<>(answers);
        for (int i = 0; i < answers2.size(); i++) {
            if (answers2.get(i).isCorrect()) {
                return i + 1;
            }
        }
        return -1;
    }

    public QuestionDto fromQuestionToQuestionDto(Question question) {
        return question == null ? null : QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .level(question.getLevel() == null ? null : fromLevelToLevelDto(question.getLevel()))
                .category(question.getCategory() == null ? null : fromCategoryToCategoryDto(question.getCategory()))
                .answers(question.getAnswers() == null ? new ArrayList<>() : question.getAnswers().stream().map(this::fromAnswerToAnswerDto).collect(Collectors.toList()))
                .goodAnswer(positionOfGoodAnswer(question.getAnswers()))
                .filename(question.getFilename())
                .build();
    }

    public Question fromQuestionDtoToQuestion(QuestionDto questionDto) {
        return questionDto == null ? null : Question.builder()
                .id(questionDto.getId())
                .text(questionDto.getText())
                .category(questionDto.getCategory() == null ? null : fromCategoryDtoToCategory(questionDto.getCategory()))
                .level(questionDto.getLevel() == null ? null : fromLevelDtoToLevel(questionDto.getLevel()))
                .answers(questionDto.getAnswers() == null ? new HashSet<>() : questionDto.getAnswers().stream().map(this::fromAnswerDtoToAnswer).collect(Collectors.toSet()))
                .filename(questionDto.getFilename())
                .build();
    }

    public LevelDto fromLevelToLevelDto(Level level) {
        return level == null ? null : LevelDto.builder()
                .id(level.getId())
                .nameLevel(level.getNameLevel())
                .build();
    }

    public Level fromLevelDtoToLevel(LevelDto levelDto) {
        return levelDto == null ? null : Level.builder()
                .id(levelDto.getId())
                .nameLevel(levelDto.getNameLevel())
                .questions(new HashSet<>())
                .build();
    }

    public QuizDto fromQuizToQuizDto(Quiz quiz) {
        return quiz == null ? null : QuizDto.builder()
                .id(quiz.getId())
                .user(fromUserToUserDto(quiz.getUser()))
                .date(quiz.getDate())
                .result(quiz.getResult())
                .build();
    }

    public Quiz fromQuizDtoToQuiz(QuizDto quizDto) {
        return quizDto == null ? null : Quiz.builder()
                .id(quizDto.getId())
                .user(fromUserDtoToUser(quizDto.getUser()))
                .date(quizDto.getDate())
                .result(quizDto.getResult())
                .build();
    }

    public GameDto fromGameToGameDto(Game game) {
        return game == null ? null : GameDto.builder()
                .id(game.getId())
                .question(game.getQuestion() == null ? null : fromQuestionToQuestionDto(game.getQuestion()))
                .quiz(game.getQuiz() == null ? null : fromQuizToQuizDto(game.getQuiz()))
                .build();
    }

    public Game fromGameDtoToGame(GameDto gameDto) {
        return gameDto == null ? null : Game.builder()
                .id(gameDto.getId())
                .question(gameDto.getQuestion() == null ? null : fromQuestionDtoToQuestion(gameDto.getQuestion()))
                .quiz(gameDto.getQuiz() == null ? null : fromQuizDtoToQuiz(gameDto.getQuiz()))
                .build();
    }

    public UserDto fromUserToUserDto(User user) {
        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .city(user.getCity())
                .bestResult(user.getBestResult())
                .build();
    }

    public User fromUserDtoToUser(UserDto userDto) {
        return userDto == null ? null : User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .city(userDto.getCity())
                .quizzes(new HashSet<>())
                .bestResult(userDto.getBestResult())
                .build();
    }
}
