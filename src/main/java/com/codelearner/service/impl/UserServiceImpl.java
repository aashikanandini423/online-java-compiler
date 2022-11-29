package com.codelearner.service.impl;

import com.codelearner.common.Role;
import com.codelearner.domain.User;
import com.codelearner.repository.UserRepository;
import com.codelearner.request.LoginUserRequest;
import com.codelearner.request.RegisterUserRequest;
import com.codelearner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setEmail(registerUserRequest.getEmail());
        user.setRole(registerUserRequest.getRole());
        user.setPassword(registerUserRequest.getPassword());
        user.setUserId(registerUserRequest.getUserId());
        userRepository.save(user);
    }

    
}
