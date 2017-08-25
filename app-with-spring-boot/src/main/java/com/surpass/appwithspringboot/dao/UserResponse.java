package com.surpass.appwithspringboot.dao;

import com.surpass.appwithspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by surpass.wei@gmail.com on 2017/8/25.
 */
@Repository
public interface UserResponse extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
