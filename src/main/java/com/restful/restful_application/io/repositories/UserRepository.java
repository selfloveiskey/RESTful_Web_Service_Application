package com.restful.restful_application.io.repositories;

import com.restful.restful_application.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/*
|--------------------------------------------------------
| Takes UserEntity class and persist data into a database
| or you can say queries the database
|--------------------------------------------------------
*/
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /*
    |---------------------------------------------------------------------------
    | If the record is found the database it will create an UserEntity object
    | and return it to the UserServiceImplementation file
    |---------------------------------------------------------------------------
    */
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId); // using String because it is a public user id
}