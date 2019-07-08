package com.jwstudio.model;

import com.jwstudio.bean.ArticlePath;
import com.jwstudio.dao.Connect;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ArticleServlet", urlPatterns = "/ArticleServlet")
public class ArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        String sql = "select * from `enword`.`article_path`";
        List<ArticlePath> paths = getArticlePath(sql);

        JSONArray jsonArray = JSONArray.fromObject(paths);
        out.write(jsonArray.toString());
        out.flush();
        out.close();
    }

    private List<ArticlePath> getArticlePath(String sql) {
        List<ArticlePath> paths = new ArrayList<>();

        Connection connection = Connect.getConnection();
        Statement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.createStatement();
            resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                ArticlePath articlePath = new ArticlePath();
                articlePath.setId(resultset.getInt("id"));
                articlePath.setPath(resultset.getString("path"));
                paths.add(articlePath);
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
