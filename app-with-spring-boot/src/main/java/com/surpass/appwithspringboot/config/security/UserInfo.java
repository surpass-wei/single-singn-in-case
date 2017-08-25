package com.surpass.appwithspringboot.config.security;

import com.surpass.appwithspringboot.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * 结合本地User类和spring security的用户信息类
 * <p>
 * Created by surpass.wei@gmail.com on 2017/7/26.
 */
public class UserInfo extends User implements UserDetails {

    public UserInfo(User user) {
        BeanUtils.copyProperties(user, this);
    }

    private static final long serialVersionUID = -1041327031937199938L;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        //  todo: 根据数据库填充authoritySet
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
