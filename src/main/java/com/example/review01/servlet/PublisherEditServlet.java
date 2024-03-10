package com.example.review01.servlet;

import com.example.review01.DAO.PublisherUtils;
import com.example.review01.conn.DBConn;
import com.example.review01.entity.Publisher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/EditPublisher")
public class PublisherEditServlet extends HttpServlet {
    public PublisherEditServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String errorString = null;
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/EditPublisher.jsp");
        String idStr = req.getParameter("id");
        Integer id = 0;
        try{
            id = Integer.parseInt(idStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (id == 0) {
            errorString = "bạn chưa chọn sản phẩm cần sửa ";
            req.setAttribute("errorString", errorString);
            dispatcher.forward(req, resp);
            return;
        }
        Connection conn = null;
        Publisher publisher = null;
        errorString = null;

        try {
            conn = DBConn.getMSSQLConnection();
            publisher = PublisherUtils.findPublisherById(conn, id);
            if (publisher == null) {
                errorString = "Không tìm thấy sản phẩm có mã " + id;

            }
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();

        }
        if (errorString == null || publisher == null) {
            req.setAttribute("errorString", errorString);
            req.setAttribute("publisher", publisher);
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String errorString = null;
        String publisherId = req.getParameter("publisherId");
        String publisherName = req.getParameter("publisherName");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        Integer id = 0;
        try{
            id = Integer.parseInt(publisherId);
        }catch (Exception e){
            e.printStackTrace();
        }
        Publisher publisher = new Publisher(id,publisherName,phone,address);
        if (errorString != null) {
            req.setAttribute("errorString", errorString);
            req.setAttribute("publisher", publisher);
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/EditPublisher.jsp");
            dispatcher.forward(req, resp);

        }

        Connection conn = null;
        try {
            conn = DBConn.getMSSQLConnection();
            PublisherUtils.updatePublisher(conn, publisher);
            resp.sendRedirect(req.getContextPath() + "/PublisherList");

        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
            req.setAttribute("errorString", errorString);
            req.setAttribute("publisher", publisher);
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/EditPublisher.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
