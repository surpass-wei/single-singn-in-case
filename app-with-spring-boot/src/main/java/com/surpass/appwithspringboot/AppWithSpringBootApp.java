package com.surpass.appwithspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

/**
 * 启动类
 * <p>
 * Created by surpass.wei@gmail.com on 2017/7/26.
 */
@Controller
@SpringBootApplication
public class AppWithSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(AppWithSpringBootApp.class, args);
    }
}
