package com.surpass.usermanagecenter.dao;

import com.surpass.usermanagecenter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by surpass.wei@gmail.com on 2017/8/3.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.password=?2 where u.id=?1 ")
    int resetUser(Long id, String password);

    @Modifying
    @Transactional
    @Query("update User u set u.freeze=?2 where u.id=?1 ")
    int freezeUser(Long id, Boolean isFreeze);

    @Query("select u from User u where u.username like concat('%',:search,'%') or u.nickname like concat('%',:search,'%')")
    Page<User> findWithSearch(@Param("search") String search, Pageable pageable);
}
