package com.lee.training.springboot.service;

import com.lee.training.springboot.model.User;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/22 20:59
 */
public interface UserService {
    User login(String username, String userpass, String loginip);
    User findUserByUserid(String userid);
    int register(String username, String userpass, String loginip);
}
