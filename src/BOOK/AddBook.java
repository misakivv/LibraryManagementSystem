package BOOK;

import java.sql.*;
import java.util.Scanner;

public class AddBook {
    public static void addbook(){
        Connection con = null;
        Statement sql;
        ResultSet rs;
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

        if(con==null) {
            System.out.printf("连接失败！！！");
            return;
        }

        Scanner in=new Scanner(System.in);
        System.out.println("请输入ISBN：");
        String a=in.nextLine();
        System.out.println("请输入书名name：");
        String b=in.nextLine();
        System.out.println("请输入作者author：");
        String c=in.nextLine();
        System.out.println("请输入类别type：");
        String d=in.nextLine();
        System.out.println("请输入存库数量number： ");
        int f = in.nextInt();
        System.out.println("请输入剩余数量remaining_number： ");
        int g = in.nextInt();

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(
                    "INSERT INTO book(ISBN,name, author,type,number,remaining_number) VALUES (?, ?, ?, ?,?,?)");
            pstmt.setString(1, a);
            pstmt.setString(2, b);
            pstmt.setString(3, c);
            pstmt.setString(4, d);
            pstmt.setInt(5, f);
            pstmt.setInt(6, g);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("添加图书成功！");

        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败：");
        }
    }
}