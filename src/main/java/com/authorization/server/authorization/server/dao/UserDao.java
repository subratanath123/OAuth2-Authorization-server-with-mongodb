package com.authorization.server.authorization.server.dao;

import com.authorization.server.authorization.server.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends MongoRepository<User, String>  {

    @Query(exists= true, value = "{email: {$exists: true, $eq: ?0}}")
    boolean isEmailExists(String email);

    @Query(value = "{email: {$regex: ?0, $options: 'i'}}")
    User findByEmail(String email);

    @Query(value = "{email: {$regex: ?0, $options: 'i'}}")
    User findByHomeAddress(String email);

}
