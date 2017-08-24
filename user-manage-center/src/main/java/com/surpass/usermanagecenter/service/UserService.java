package com.surpass.usermanagecenter.service;


import com.surpass.usermanagecenter.bean.AddUserRes;
import com.surpass.usermanagecenter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by surpass.wei@gmail.com on 2017/8/3.
 */
@WebService
public interface UserService {

    Long save(User user);

    void remove(Long id);

    void remove(List<Long> idList);

    List<User> findAll(User user);

    List<User> findAll();

    /**
     * 检查用户名是否存在
     *
     * @param username
     * @return 存在：true
     */
    boolean userNameIsExist(String username);

    /**
     * 检查用户名是否合法
     *
     * @param username
     * @return
     */
    boolean userNameIsLegal(String username);

    /**
     * 重置用户密码
     *
     * @param id
     * @return
     */
    boolean resetUser(Long id);

    /**
     * 冻结/解除冻解用户
     *
     * @param id
     * @return
     */
    boolean freezeUser(Long id, Boolean isFreeze);

    /**
     * 对用户名和昵称进行模糊查询
     *
     * @param search
     * @param pageable
     * @return
     */
    Page<User> findByPage(String search, Pageable pageable);

    /**
     * 注册一个新用户
     * @param username  用户名(应当为字母开头，4~15个字符)
     * @param password  原始密码
     * @param nickname  昵称
     * @return CommonRes
     */
    AddUserRes addUser(String username, String password, String nickname);

    /**
     * 更新用户密码
     * @param username
     * @param password
     * @return
     */
    User updatePassword(String username, String password);

    /**
     * 更新昵称
     * @param username
     * @param nickname
     * @return
     */
    User updateNickname(String username, String nickname);

    /**
     * 更新登录时间为当前时间
     * @param username
     * @return
     */
    User updateLoginTime(String username);
}
