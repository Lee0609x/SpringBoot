package com.lee.training.springboot.service.impl;

import com.lee.training.springboot.dao.UserDao;
import com.lee.training.springboot.model.User;
import com.lee.training.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/22 21:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String userpass, String loginip) {
        return userDao.selectUser(username, userpass);
    }

    @Override
    public User findUserByUserid(String userid) {
        return userDao.selectUserByUserid(userid);
    }

    @Override
    public int register(String username, String userpass, String loginip) {
        //日期毫秒 + 四位随机数
        String userid = String.valueOf(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
        return userDao.insertUser(new User(userid, username, userpass, new Timestamp(new Date().getTime()), loginip));
    }
}
