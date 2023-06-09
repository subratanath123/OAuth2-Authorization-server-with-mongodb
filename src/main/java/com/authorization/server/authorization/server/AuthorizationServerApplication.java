package com.authorization.server.authorization.server;

import com.authorization.server.authorization.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AuthorizationServerApplication implements CommandLineRunner {

    @Autowired
    private UserDao  userDao;

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
