package com.restful.restful_application.ui.controller;

import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.dto.UserDto;
import com.restful.restful_application.ui.model.request.UserDetailsRequestModel;
import com.restful.restful_application.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*
|-------------------------------------------------------------
| Responsible for all operations that have to do with the user
|-------------------------------------------------------------
*/
@RestController
@RequestMapping("users") //http://localhost:8080/users
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        /*
        |----------------------------------------
        | Copy info from userDetails into userDto
        |----------------------------------------
        */
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        /*
        |--------------------------------------------------------------
        | Use userDto to create a new user
        | then copy the new user info from createdUser to returnValue
        |--------------------------------------------------------------
        */
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        /*
        |----------------------------------------
        | Returns as an outgoing JSON response
        |----------------------------------------
        */
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