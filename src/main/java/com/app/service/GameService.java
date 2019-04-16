package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.dto.GameDto;
import com.app.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;
    private ModelMapperService modelMapperService;

    public GameService(GameRepository gameRepository, ModelMapperService modelMapperService) {
        this.gameRepository = gameRepository;
        this.modelMapperService = modelMapperService;
    }


    public void addGame(GameDto game) {
        if (game == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD GAME - GAME IS NULL");
        }
        gameRepository.add(modelMapperService.fromGameDtoToGame(game));
    }

    public void updateGame(GameDto game) {
        if (game == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE GAME - GAME IS NULL");
        }
        gameRepository.update(modelMapperService.fromGameDtoToGame(game));
    }

    public void deleteGame(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE GAME - ID IS NULL");
        }
        gameRepository.delete(id);
    }

    public Optional<GameDto> findOneGame(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE GAME - ID IS NULL");
        }
        return gameRepository.findById(id).map(p -> modelMapperService.fromGameToGameDto(p));
    }

    public List<GameDto> findAllGame() {
        return gameRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromGameToGameDto(p))
                .collect(Collectors.toList());
    }
}
