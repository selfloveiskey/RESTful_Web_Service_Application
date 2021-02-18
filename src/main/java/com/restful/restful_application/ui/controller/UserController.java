package com.restful.restful_application.ui.controller;

import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.dto.UserDto;
import com.restful.restful_application.ui.model.request.UserDetailsRequestModel;
import com.restful.restful_application.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") //http://localhost:8080/users
/*
//-|-------------------------------------------------------------
//-| Responsible for all operations that have to do with the user
//-|-------------------------------------------------------------
*/
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}