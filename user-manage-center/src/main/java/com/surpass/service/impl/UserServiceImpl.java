package com.surpass.service.impl;


import com.surpass.bean.AddUserRes;
import com.surpass.dao.UserRepository;
import com.surpass.entity.User;
import com.surpass.service.UserService;
import com.surpass.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by surpass.wei@gmail.com on 2017/8/3.
 */
@Service
public class UserServiceImpl implements UserService {
    /*字母开头，4~15个字符*/
    private static final String USERNAME_REGEX = "^[a-zA-z]\\w{3,15}$";

    @Value("${default.user.password}")
    private String defaultPassword;

    @Resource
    private UserRepository userRepository;

    @Override
    public Long save(User user) {
        User save = userRepository.save(user);
        return save.getId();
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void remove(List<Long> idList) {
        List<User> userList = userRepository.findAll(idList);
        userRepository.deleteInBatch(userList);
    }

    @Override
    public List<User> findAll(User user) {
        Example<User> userExample = Example.of(user);
        return userRepository.findAll(userExample);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findByPage(String search, Pageable pageable) {
        return userRepository.findWithSearch(search, pageable);
    }

    @Override
    public AddUserRes addUser(String username, String password, String nickname) {
        AddUserRes addUserRes = new AddUserRes();

        /*用户名校验*/
        boolean isLegal = this.userNameIsLegal(username);
        boolean isExist = this.userNameIsExist(username);
        if (!isLegal) {
            addUserRes.setSuccess(false);
            addUserRes.setMsg("用户名非法（应当为字母开头，4~15个字符）");
            return addUserRes;
        } else if (isExist) {
            addUserRes.setSuccess(false);
            addUserRes.setMsg("用户名已存在");
            return addUserRes;
        }

        /*初始化用户*/
        User user = new User();
        user.setUsername(username);
        if (password != null) {
            user.setPassword(MD5Util.encrypt(password));
        } else {
            user.setPassword(MD5Util.encrypt(defaultPassword));
        }
        user.setNickname(nickname);
        user.setRegisterTime(new Date());
        user.setFreeze(false);
        Long userId = this.save(user);

        /*构建返回结果*/
        if (userId != null) {
            addUserRes.setSuccess(true);
            addUserRes.setUserId(userId);
            addUserRes.setEncryptPassword(user.getPassword());
            addUserRes.setMsg("注册成功");
        } else {
            addUserRes.setSuccess(false);
            addUserRes.setMsg("注册失败");
        }
        return addUserRes;
    }

    @Override
    public User updatePassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        user.setPassword(MD5Util.encrypt(password));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateNickname(String username, String nickname) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        user.setNickname(nickname);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateLoginTime(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        user.setLastLogin(new Date());
        return userRepository.saveAndFlush(user);
    }

    @Override
    public boolean userNameIsExist(String username) {
        User byUsername = userRepository.findByUsername(username);
        return byUsername != null;
    }

    @Override
    public boolean userNameIsLegal(String username) {
        return username.matches(USERNAME_REGEX);
    }

    @Override
    public boolean resetUser(Long id) {
        int i = userRepository.resetUser(id, defaultPassword);
        return i > 0;
    }

    @Override
    public boolean freezeUser(Long id, Boolean isFreeze) {
        int i = userRepository.freezeUser(id, isFreeze);
        return i > 0;
    }

}
