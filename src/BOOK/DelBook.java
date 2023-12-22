//package BOOK;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class DelBook {
//    public static void delbook(){
//        Connection con = null;
//        Statement sql;
//        ResultSet rs;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        String uri = "jdbc:mysql://localhost:3306/library?" +
//                "useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
//        String user = "root";
//        String password = "123";
//        try {
//            con = DriverManager.getConnection(uri, user, password);
//        } catch (SQLException e) {
//        }
//        if(con==null) {
//            System.out.printf("连接失败！！！");
//            return;
//        }
//
//        Scanner in=new Scanner(System.in);
//        System.out.println("请输入书名name：");
//        String a=in.next();
//        System.out.println("请输入作者author：");
//        String b=in.next();
//        System.out.println("请输入ISBN：");
//        String c=in.next();
//        System.out.println("请输入类别type：");
//        String d=in.next();
//        PreparedStatement pstmt = null;
//        try {
//            pstmt = con.prepareStatement(
//                    "DELETE FROM book WHERE name=? AND author=? AND ISBN=? AND type=?");
//            pstmt.setString(1, a);
//            pstmt.setString(2, b);
//            pstmt.setString(3, c);
//            pstmt.setString(4, d);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("删除图书成功！");
//
//    }
//}
//
//







package BOOK;

import java.sql.*;
import java.util.Scanner;

public class DelBook {
    public static void delbook() {
        Connection con = null;
        Statement sql;
        ResultSet rs;
        PreparedStatement pstmt = null;
        ResultSet rsStatus = null;

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
            System.out.println("连接失败！");
            return;
        }

        if (con == null) {
            System.out.println("连接失败！！！");
            return;
        }

        Scanner in = new Scanner(System.in);
        System.out.println("请输入书名name：");
        String a = in.next();
        System.out.println("请输入作者author：");
        String b = in.next();
        System.out.println("请输入ISBN：");
        String c = in.next();
        System.out.println("请输入类别type：");
        String d = in.next();

        try {
            pstmt = con.prepareStatement("SELECT status FROM book WHERE name=? AND author=? AND ISBN=? AND type=?");
            pstmt.setString(1, a);
            pstmt.setString(2, b);
            pstmt.setString(3, c);
            pstmt.setString(4, d);
            rsStatus = pstmt.executeQuery();

            if (rsStatus.next()) {
                int status = rsStatus.getInt("status");
                if (status == 1) {
                    System.out.println("该图书已被借出，无法删除！");
                } else {
                    pstmt = con.prepareStatement(
                            "DELETE FROM book WHERE name=? AND author=? AND ISBN=? AND type=?");
                    pstmt.setString(1, a);
                    pstmt.setString(2, b);
                    pstmt.setString(3, c);
                    pstmt.setString(4, d);
                    pstmt.executeUpdate();
                    System.out.println("删除图书成功！");
                }
            } else {
                System.out.println("未找到该图书！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rsStatus != null) {
                    rsStatus.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}