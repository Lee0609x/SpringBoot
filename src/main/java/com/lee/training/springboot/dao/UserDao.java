package com.lee.training.springboot.dao;

import com.lee.training.springboot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/22 21:03
 * 查询SQL常量名规则：方法名_参数1_参数2...
 */
@Mapper
@Repository
public interface UserDao {
    String SELECT_USER_USERNAME_USERPASS = "SELECT USER_ID userid, USER_NAME username, USER_PASS userpass, JOIN_TIME jointime, LOGIN_IP loginip FROM USER WHERE USER_NAME = #{username} AND USER_PASS = #{userpass}";
    String SELECT_USER_USERID = "SELECT USER_ID userid, USER_NAME username, USER_PASS userpass, JOIN_TIME jointime, LOGIN_IP loginip FROM USER WHERE USER_ID = #{userid}";
    String INSERT_USER = "INSERT INTO USER (USER_ID, USER_NAME, USER_PASS, JOIN_TIME, LOGIN_IP) VALUES (#{user.userid}, #{user.username}, #{user.userpass}, #{user.jointime}, #{user.loginip})";
    @Select(SELECT_USER_USERNAME_USERPASS)
    User selectUser(@Param("username") String username, @Param("userpass") String userpass);
    @Select(SELECT_USER_USERID)
    User selectUserByUserid(@Param("userid") String userid);
    @Insert(INSERT_USER)
    int insertUser(@Param("user") User user);
}
