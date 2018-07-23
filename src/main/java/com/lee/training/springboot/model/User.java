package com.lee.training.springboot.model;

import java.sql.Timestamp;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/22 20:52
 */
public class User {
    public User(String userid, String username, String userpass, Timestamp jointime, String loginip) {
        this.userid = userid;
        this.username = username;
        this.userpass = userpass;
        this.jointime = jointime;
        this.loginip = loginip;
    }

    private String userid;
    private String username;
    private String userpass;
    private Timestamp jointime;
    private String loginip;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public Timestamp getJointime() {
        return jointime;
    }

    public void setJointime(Timestamp jointime) {
        this.jointime = jointime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }
}
