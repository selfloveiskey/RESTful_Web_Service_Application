package com.restful.restful_application.service.implementation;

import com.restful.restful_application.UserRepository;
import com.restful.restful_application.io.entity.UserEntity;
import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.Utils;
import com.restful.restful_application.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {
        // Check to see if user already exists in the database
        UserEntity emailDuplicateCheck = userRepository.findByEmail(user.getEmail());

        if(emailDuplicateCheck != null){
            throw new RuntimeException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword("test");

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}