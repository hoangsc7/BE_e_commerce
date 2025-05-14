package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUserById(Long userid) throws UserException;

    public User findUserProfileById(String jwt) throws UserException;
}
