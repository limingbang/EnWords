package com.jwstudio.model;

import com.jwstudio.dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("account");
        String password = request.getParameter("password");
        System.out.println("account:" + name + ",password:" + password);

        if (!Dao.isCheckPhoneExist(name)) {
            out.write("not_exist");
            out.flush();
        } else {
            if (!Dao.isCheckPasswordTrue(password)) {
                out.write("error_password");
                out.flush();
            } else {
                out.write("success");
                out.flush();
            }
        }

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
