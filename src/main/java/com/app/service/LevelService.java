package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.LevelPoint;
import com.app.model.dto.LevelDto;
import com.app.repository.LevelRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LevelService {

    private LevelRepository levelRepository;
    private ModelMapperService modelMapperService;

    public LevelService(LevelRepository levelRepository, ModelMapperService modelMapperService) {
        this.levelRepository = levelRepository;
        this.modelMapperService = modelMapperService;
    }

    public void addLevel(LevelDto level) {
        if (level == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD LEVEL - LEVEL IS NULL");
        }
        levelRepository.add(modelMapperService.fromLevelDtoToLevel(level));
    }

    public void updateLevel(LevelDto level) {
        if (level == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE LEVEL - LEVEL IS NULL");
        }
        levelRepository.update(modelMapperService.fromLevelDtoToLevel(level));
    }

    public void deleteLevel(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE LEVEL - ID IS NULL");
        }
        levelRepository.delete(id);
    }

    public Optional<LevelDto> findOneLevel(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE LEVEL - ID IS NULL");
        }
        return levelRepository.findById(id).map(p -> modelMapperService.fromLevelToLevelDto(p));
    }

    public List<LevelDto> findAllLevel() {
        return levelRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromLevelToLevelDto(p))
                .collect(Collectors.toList());
    }

    public void addAllLevel() {
        List<LevelDto> levelList = findAllLevel();
        Optional<LevelDto> levelLack = levelList.stream().filter(s -> s.getNameLevel().equals(LevelPoint.ALL)).findFirst();
        Optional<LevelDto> levelEasy = levelList.stream().filter(s -> s.getNameLevel().equals(LevelPoint.EASY)).findFirst();
        Optional<LevelDto> levelNormal = levelList.stream().filter(s -> s.getNameLevel().equals(LevelPoint.NORMAL)).findFirst();
        Optional<LevelDto> levelHard = levelList.stream().filter(s -> s.getNameLevel().equals(LevelPoint.HARD)).findFirst();
        if (!levelLack.isPresent()) {
            addLevel(new LevelDto().builder().nameLevel(LevelPoint.ALL).build());
        }
        if (!levelEasy.isPresent()) {
            addLevel(new LevelDto().builder().nameLevel(LevelPoint.EASY).build());
        }
        if (!levelNormal.isPresent()) {
            addLevel(new LevelDto().builder().nameLevel(LevelPoint.NORMAL).build());
        }
        if (!levelHard.isPresent()) {
            addLevel(new LevelDto().builder().nameLevel(LevelPoint.HARD).build());
        }
    }
}
