package com.example.review01.servlet;

import com.example.review01.DAO.PublisherUtils;
import com.example.review01.conn.DBConn;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/DeletePublisher")
public class PublisherDeleteServlet extends HttpServlet {
    public PublisherDeleteServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorString = null;
        String idS = req.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.parseInt(idS);
        }catch (Exception e){
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DBConn.getMSSQLConnection();
            PublisherUtils.deletePublisherById(conn,id);
        }catch (Exception e){
            e.printStackTrace();
            errorString = e.getMessage();
        }
        if (errorString != null){
            req.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/DeletePublisherError.jsp");
            dispatcher.forward(req,resp);
        }
        else {
            resp.sendRedirect(req.getContextPath()+"/PublisherList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
