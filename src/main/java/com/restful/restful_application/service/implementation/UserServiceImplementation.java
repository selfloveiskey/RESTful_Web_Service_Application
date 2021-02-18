package com.restful.restful_application.service.implementation;

import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Override
    public UserDto createUser(UserDto user) {
        return null;
    }
}