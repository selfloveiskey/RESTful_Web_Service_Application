package com.restful.restful_application;

import com.restful.restful_application.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/*
|--------------------------------------------------------
| Takes UserEntity class and persist data into a database
| or you can say queries a database
|--------------------------------------------------------
*/
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}