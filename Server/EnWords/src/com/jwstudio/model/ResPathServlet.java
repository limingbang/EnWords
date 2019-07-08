package com.jwstudio.model;

import com.jwstudio.bean.MyDate;
import com.jwstudio.bean.ResPath;
import com.jwstudio.dao.Connect;
import com.jwstudio.dao.Dao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ResPathServlet", urlPatterns = "/ResPathServlet")
public class ResPathServlet extends HttpServlet {

    private static final String TYPE_RES_PATH = "1"; // 加载资源路径
    private static final String TYPE_PUNCH_CARD = "2"; // 打卡
    private static final String TYPE_RES_PATH_DATE = "3"; // 获取打卡日期所对应的资源
    private static final String TYPE_DATE = "4"; // 获取打卡日期

    List<ResPath> resPaths = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        String name = request.getParameter("name");
        System.out.println("type:" + type + ",name:" + name);

        switch (type) {
            case TYPE_RES_PATH:
                int plan = Integer.parseInt(request.getParameter("plan"));
                if (!Dao.isCheckLearned(name)) {
                    String selectPath = "select * from `enword`.`res_path` order by id limit 0," + plan;
                    resPaths = getNewResPath(selectPath);
                    recordLearn(resPaths, name);

                    JSONArray jsonArray = JSONArray.fromObject(resPaths);
                    out.write(jsonArray.toString());
                    out.flush();
                } else {
                    String maxResId = "select max(resId) from `enword`.`res_user` where userId = " +
                            "(select id from `enword`.`user` where name = '" +
                            name + "')";

                    int resId = getMaxResId(maxResId);
                    String selectNewPath = "select * from `enword`.`res_path` order by id limit " + resId + "," + plan;
                    resPaths = getNewResPath(selectNewPath);
                    recordLearn(resPaths, name);

                    JSONArray jsonArray = JSONArray.fromObject(resPaths);
                    out.write(jsonArray.toString());
                    out.flush();
                }
                break;
            case TYPE_PUNCH_CARD:
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String today = simpleDateFormat.format(date);

                if (punchCard(resPaths, name, today)) {
                    out.write("success");
                    out.flush();
                } else {
                    out.write("fail");
                    out.flush();
                }
                break;
            case TYPE_RES_PATH_DATE:
                String d = request.getParameter("date");
                System.out.println("date:" + d);

                String selectNewPath = "select `enword`.`res_path`.id,`enword`.`res_path`.path from `enword`.`res_user` " +
                        "left outer join `enword`.`res_path` on `enword`.`res_user`.resId = `enword`.`res_path`.id " +
                        "where `enword`.`res_user`.date = '" +
                        d + "'and `enword`.`res_user`.userId = (select id from `enword`.`user` where name = '" +
                        name + "')";

                List<ResPath> oldResPath = getNewResPath(selectNewPath);
                JSONArray jsonArray = JSONArray.fromObject(oldResPath);
                out.write(jsonArray.toString());
                out.flush();
                break;
            case TYPE_DATE:
                JSONArray dateJsonArray = JSONArray.fromObject(getPunchCardDate(name));
                out.write(dateJsonArray.toString());
                out.flush();
                break;
            default:
                break;
        }

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean punchCard(List<ResPath> paths, String name, String date) {
        boolean success = false;
        Connection connection = Connect.getConnection();
        PreparedStatement pstmt = null;
        String punchCard = "update `enword`.`res_user` set date = ? where resId = ? and " +
                "userId = (select id from `enword`.`user` where name = ?)";

        try {
            pstmt = connection.prepareStatement(punchCard);
            for (int i = 0; i < paths.size(); i++) {
                pstmt.setString(1, date);
                pstmt.setInt(2, paths.get(i).getId());
                pstmt.setString(3, name);
                pstmt.addBatch();
            }
            int temp[] = pstmt.executeBatch();
            System.out.println("temp[]:" + temp.length);
            if (temp.length > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    private List<MyDate> getPunchCardDate(String name) {
        List<MyDate> dates = new ArrayList<>();

        Connection connection = Connect.getConnection();
        Statement statement = null;
        ResultSet resultset = null;

        String sql = "select distinct date from `enword`.`res_user` where userId = (select id from `enword`.`user` where name = '" +
                name + "')";

        try {
            statement = connection.createStatement();
            resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                MyDate d = new MyDate();
                d.setDate(resultset.getString("date"));
                dates.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return dates;
    }

    private int getMaxResId(String sql) {
        int maxResId = 0;

        Connection connection = Connect.getConnection();
        Statement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.createStatement();
            resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                maxResId = resultset.getInt(1);
                System.out.println("max:" + maxResId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return maxResId;
    }

    private void recordLearn(List<ResPath> resPaths, String name) {
        Connection connection = Connect.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            for (int i = 0; i < resPaths.size(); i++) {
                int resId = resPaths.get(i).getId();
                String sql = "insert into `enword`.`res_user` (resId,userId) values(" +
                        resId + ",(select id from user where name = '" +
                        name + "'))";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<ResPath> getNewResPath(String sql) {
        List<ResPath> paths = new ArrayList<>();

        Connection connection = Connect.getConnection();
        Statement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.createStatement();
            resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                ResPath resPath = new ResPath();
                resPath.setId(resultset.getInt("id"));
                resPath.setPath(resultset.getString("path"));
                paths.add(resPath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return paths;
    }

}
