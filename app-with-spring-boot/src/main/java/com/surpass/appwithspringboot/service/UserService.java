package com.surpass.appwithspringboot.service;

import com.surpass.appwithspringboot.entity.User;

/**
 * Created by surpass.wei@gmail.com on 2017/8/25.
 */
public interface UserService {
    User addUser(User user);

    User findByUsername(String username);
}
