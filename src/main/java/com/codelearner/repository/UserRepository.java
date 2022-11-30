package com.codelearner.repository;

import com.codelearner.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserIdAndPassword(String userId, String password);

    List<User> findByRole(String role);

}
