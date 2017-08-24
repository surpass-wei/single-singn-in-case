package com.surpass.usermanagecenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring security 的配置
 * <p>
 * Created by surpass.wei@gmail.com on 2017/8/24.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final int A_WEEK_SECOND = 60 * 60 * 24 * 7;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/logout","/ws/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();

        http.rememberMe().tokenValiditySeconds(A_WEEK_SECOND);
        http.csrf().disable();  //  关闭CSRF
    }
}
