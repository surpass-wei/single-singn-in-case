package com.surpass.appwithspringboot.config.security;

import com.surpass.appwithspringboot.entity.User;
import com.surpass.appwithspringboot.service.UserService;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息服务
 * 因为要集成cas，实现的则是AuthenticationUserDetailsService接口
 * <p>
 * Created by surpass.wei@gmail.com on 2017/7/26.
 */
@Component
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        User user = userService.findByUsername(token.getName());
        UserInfo userInfo = new UserInfo(user);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("user_read"));
        userInfo.setAuthorities(authorities);
        return userInfo;
    }
}
