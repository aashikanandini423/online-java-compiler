package com.codelearner.service;

import com.codelearner.domain.User;
import com.codelearner.request.LoginUserRequest;
import com.codelearner.request.RegisterUserRequest;

import java.util.List;

public interface UserService {

    void registerUser(RegisterUserRequest registerUserRequest);

    User login(LoginUserRequest loginUserRequest);

    List<String> getAllStudents();
}
