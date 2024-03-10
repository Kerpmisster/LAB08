package com.example.review01.servlet;

import com.example.review01.DAO.BookUtils;
import com.example.review01.conn.DBConn;
import com.example.review01.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/AddBookServlet")
public class BookCreateServlet extends HttpServlet {
    public BookCreateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/CreateBook.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String errorString = null;
        String bookId = req.getParameter("bookId");
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String releaseYear = req.getParameter("releaseYear");
        String priceStr = req.getParameter("price");
        String picture = req.getParameter("picture");
        String publisherIdStr = req.getParameter("publisherId");
        String categoryIdStr = req.getParameter("categoryId");
        Integer release = 0;
        Double price = 0.0;
        Integer publisherId = 0;
        Integer categoryId = 0;
        try {
            publisherId = Integer.parseInt(publisherIdStr);
            categoryId = Integer.parseInt(categoryIdStr);
            release = Integer.parseInt(releaseYear);
            price = Double.parseDouble(priceStr);
        }catch (Exception e){
            errorString = e.getMessage();
        }

        Book book = new Book(bookId,title,author,release,price,picture,publisherId,categoryId);
        if (errorString != null) {
            req.setAttribute("errorString", errorString);
            req.setAttribute("book", book);
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/CreateBook.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        Connection conn = null;
        try {
            conn = DBConn.getMSSQLConnection();
            BookUtils.addBook(conn, book);
            resp.sendRedirect(req.getContextPath() + "/BookList");
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
            req.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/CreateBook.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
