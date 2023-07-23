package com.authorization.server.authorization.server.dao;

import com.authorization.server.authorization.server.entity.application.ApplicationClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationClientDao extends MongoRepository<ApplicationClient, String> {

    @Query(value = "{clientId: {$eq: ?0}}")
    ApplicationClient findByClientId(String clientId);

}
