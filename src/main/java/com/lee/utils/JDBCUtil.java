package com.lee.utils;

import com.lee.training.springboot.model.User;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

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
    private static ResultSet resultSet;

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            driverName = properties.getProperty("spring.datasource.driver-class-name");
            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
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
    public static int AutoUpdate(String sql, String... params) {
        if (connection == null) {
            init();
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0; i<params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return -1;
    }

    /**
     * 支持commit和rollback的事务的增/删/改方法
     * @return
     */
    public static int Update(String sql, String... params) {
        if (connection == null) {
            init();
        }
        try {
            connection.setAutoCommit(false);
            return AutoUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return -1;
    }

    /**
     * 查询
     * @param clazz 返回值类型
     * @param sql SQL语句
     * @param params SQL参数
     * @param <T>
     * @return
     */
    public static <T> List<T> select(Class<T> clazz, String sql, Object... params) {
        if (connection == null) {
            init();
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0; i<params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //创建 ResultSetMetaData 来获得数据库表的字段名等元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //存放查询出的数据, key:列名, value:列值
            Map<String, Object> map = new HashMap();
            //最终结果集
            List resultList = new ArrayList<T>();
            T entity;
            while (resultSet.next()) {
                map.clear();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(columnLabel);
                    map.put(columnLabel, columnValue);
                }
                if (!map.isEmpty()) {
                    entity = clazz.newInstance();
                    //遍历 Map 对象, 利用反射为 Class 对象的对应的属性赋值
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String name = entry.getKey();
                        Object value = entry.getValue();
                        //利用反射获取实体类的字段
                        Field field = clazz.getDeclaredField(name);
                        //设置为true可以访问private变量
                        field.setAccessible(true);
                        field.set(entity, value);
                    }
                    resultList.add(entity);
                }
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    public static boolean commit() {
        try {
            if (connection != null || !connection.getAutoCommit()) {
                System.out.println("connection尚未初始化 || 自动提交已开启");
                return false;
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean roolback() {
        try {
            if (connection != null || !connection.getAutoCommit()) {
                System.out.println("connection尚未初始化 || 自动提交已开启");
                return false;
            }
            connection.rollback();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public static void main(String[] args) {
        List<User> select = select(User.class, "SELECT USER_ID userid, USER_NAME username, USER_PASS userpass, JOIN_TIME jointime, LOGIN_IP loginip" +
                " FROM USER WHERE USER_ID = ?", "1");
        System.out.println(select.get(0).toString());
    }
}
