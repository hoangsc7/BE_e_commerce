package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.UserException;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> viewUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileById(jwt);
        return ResponseEntity.ok(user);
    }
}
