package com.lee.training.springboot.controller;

import com.lee.training.springboot.model.User;
import com.lee.training.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/22 21:32
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService userService;

    @PostMapping("login")
    public User login(String username, String userpass) {
        return userService.login(username, userpass, "test");
    }
    @GetMapping(value = "{userid}")
    public User findByUserid(@PathVariable("userid") String userid) {
        return userService.findUserByUserid(userid);
    }
    @PostMapping("register")
    public int register(String username, String userpass) {
        return userService.register(username, userpass, "测试");
    }
}
