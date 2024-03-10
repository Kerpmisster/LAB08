package com.example.review01.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    public static Connection getMSSQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "LAPTOP-SSHUBNQA";
        String dbName = "BookStore";
        String userName = "sa";
        String password = "123456";
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433;databaseName=" + dbName + ";user=" + userName + ";password=" + password + ";encrypt=true;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = (Connection) DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }
    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Get connection ... ");
        // Lấy ra đối tượng Connection kết nối vào database.
        Connection conn = DBConn.getMSSQLConnection();
        System.out.println(conn.getCatalog());
        System.out.println("Done!");
    }
}
