package com.jwstudio.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

    /**
     * 检查新注册的账号是否存在
     *
     * @param name
     * @return
     */
    public static boolean isCheckPhoneExist(String name) {
        String sql = "select * from `enword`.`user` where name = '" + name + "'";
        return checkUserInfo(sql);
    }

    /**
     * 检查密码是否正确
     *
     * @param password
     * @return
     */
    public static boolean isCheckPasswordTrue(String password) {
        String sql = "select * from `enword`.`user` where password = '" + password + "'";
        return checkUserInfo(sql);
    }

    /**
     * 检查该name是否存在学习经历
     *
     * @param name
     * @return
     */
    public static boolean isCheckLearned(String name) {
        String sql = "select * from `enword`.`res_user` where userId = " +
                "(select id from `enword`.`user` where name = '" + name + "')";
        return checkUserInfo(sql);
    }

    /**
     * 向数据库插入信息
     *
     * @param sql
     * @return
     */
    public static boolean insertInfo(String sql) {
        boolean success = false;
        Connection connection = Connect.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            int count = statement.executeUpdate(sql);
            if (count > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    private static boolean checkUserInfo(String sql) {
        boolean result = false;

        Statement statement = null;
        ResultSet resultSet = null;
        Connection conn = Connect.getConnection();

        int count = 0;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count++;
            }
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
