package BOOK;

//import BOOK.Book;
//import aa.BookList;

import java.sql.*;
import java.util.Scanner;

public class FindBook {
    public static void find() {
        Connection con = null;
        Statement sql;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String uri = "jdbc:mysql://localhost:3306/library?" +
                "useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
        String user = "root";
        String password = "123";
        try {
            con = DriverManager.getConnection(uri, user, password);
        } catch (SQLException e) {
            System.out.println("连接失败：" + e.getMessage());
            return;
        }
        if (con == null) {
            System.out.printf("连接失败！！！");
            return;
        }

        Scanner in = new Scanner(System.in);
        System.out.println("请输入你要查找的条件：书名（name）或ISBN");
        String conditionType = in.next().toLowerCase();
        System.out.println("请输入" + conditionType + "：");
        String searchValue = in.next();

        PreparedStatement pstmt = null;
        try {
            String query;
            switch (conditionType) {
                case "name":
                    query = "SELECT * FROM book WHERE name=?";
                    break;
                case "isbn":
                    query = "SELECT * FROM book WHERE ISBN=?";
                    break;
                default:
                    System.out.println("无效的查找条件，请输入书名（name）或ISBN");
                    return;
            }

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, searchValue);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("查找成功，以下是相关图书信息：");
                do {
                    System.out.println("书名: " + rs.getString("name"));
                    System.out.println("作者: " + rs.getString("author"));
                    System.out.println("ISBN: " + rs.getString("ISBN"));
                    System.out.println("类型: " + rs.getString("type"));
                    System.out.println("数量: " + rs.getInt("number"));
                    System.out.println("剩余数量: " + rs.getInt("remaining_number"));
                    System.out.println("------------------------");
                } while (rs.next());
            } else {
                System.out.println("查无此书！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("关闭资源时发生错误：" + e.getMessage());
            }
        }
    }
}