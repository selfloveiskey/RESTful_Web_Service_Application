package com.restful.restful_application.service;

import com.restful.restful_application.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
}