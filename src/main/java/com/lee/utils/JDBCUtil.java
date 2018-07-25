package com.lee.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/25 19:11
 * 数据库连接工具类
 */
public class JDBCUtil {
    private static String driverName;
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public static String sql = "SELECT * FROM USER WHERE USER_ID = ?";

    static {
        try {
            Properties properties = new Properties();
            JDBCUtil.class.getClassLoader().getResourceAsStream("");
            driverName = properties.getProperty("driverName");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取数据库配置文件失败");
        }
    }

    public static void init() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }

    }

    /**
     * 自动提交事务的增/删/改方法
     * @return
     */
    public static boolean AutoUpdate(String sql, String... params) {
        if (connection == null) {
            init();
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 需要commit的事务的增/删/改方法
     * @return
     */
    public static boolean Update(String sql, String... params) {
        if (connection == null) {
            init();
        }
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object select(String sql, String... params) {
        if (connection == null) {
            init();
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0; i<params.length; i++) {
                preparedStatement.setString(i+1, params[i]);
            }
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean commit() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                System.out.println("connection尚未初始化 || 自动提交已开启");
                return false;
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean roolback() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                System.out.println("connection尚未初始化 || 自动提交已开启");
                return false;
            }
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void close() {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
