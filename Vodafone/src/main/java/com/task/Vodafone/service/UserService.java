package com.task.Vodafone.service;

import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
