package com.surpass.appwithspringboot.service.impl;

import com.surpass.appwithspringboot.dao.UserResponse;
import com.surpass.appwithspringboot.entity.User;
import com.surpass.appwithspringboot.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by surpass.wei@gmail.com on 2017/8/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserResponse userResponse;

    @Override
    public User addUser(User user) {
        return userResponse.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userResponse.findByUsername(username);
    }
}
