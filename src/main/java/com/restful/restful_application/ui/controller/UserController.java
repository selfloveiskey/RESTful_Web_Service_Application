package com.restful.restful_application.ui.controller;

import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.dto.UserDto;
import com.restful.restful_application.ui.model.request.UserDetailsRequestModel;
import com.restful.restful_application.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    /*
    |-------------------------------------------------------------------------
    | produces = {}
    |  - Order of media types matter if you have more than one listed
    |  - Will return XML or JSON.
    |  - Will return XML first if it can b/c of the order they are listed in
    |-------------------------------------------------------------------------
    */
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    /*
    |-------------------------------------------------------------------------
    | consumes = Can consume information in either XML or JSON
    | produces = Can respond back with either XML or JSON
    |-------------------------------------------------------------------------
    */
    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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