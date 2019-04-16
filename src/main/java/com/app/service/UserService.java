package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.dto.UserDto;
import com.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private ModelMapperService modelMapperService;

    public UserService(UserRepository userRepository, ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
    }

    public void addUser(UserDto user) {
        if (user == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD USER - USER IS NULL");
        }
        userRepository.add(modelMapperService.fromUserDtoToUser(user));
    }

    public void updateUser(UserDto user) {
        if (user == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE USER - USER IS NULL");
        }
        userRepository.update(modelMapperService.fromUserDtoToUser(user));
    }

    public void deleteUser(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE USER - ID IS NULL");
        }
        userRepository.delete(id);
    }

    public Optional<UserDto> findOneUser(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE USER - ID IS NULL");
        }
        return userRepository.findById(id).map(p -> modelMapperService.fromUserToUserDto(p));
    }

    public List<UserDto> findAllUser() {
        return userRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromUserToUserDto(p))
                .collect(Collectors.toList());
    }
}
