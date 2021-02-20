package com.restful.restful_application.service;

import com.restful.restful_application.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

//-| Will add more methods later
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
}