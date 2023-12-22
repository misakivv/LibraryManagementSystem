package User;

import java.sql.*;
import java.util.Scanner;

public class DelUser {
    public static void deluser(){
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
        }
        if(con==null) {
            System.out.printf("连接失败！！！");
            return;
        }

        Scanner in=new Scanner(System.in);
        System.out.println("请输入用户名（由4-20个字符组成，可以包含字母、数字、下划线）：");
        String a=in.next();
        System.out.println("请输入密码（由8-16个字符组成，至少包含一个大写字母、一个小写字母和一个数字）：");
        String b=in.next();
        System.out.println("请输入身份证号id_card：");
        String c=in.next();
        System.out.println("请输入手机号phone_number：");
        String d=in.next();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(
                    "DELETE FROM register WHERE username=? AND password=?");
            pstmt.setString(1, a);
            pstmt.setString(2, b);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        System.out.println("删除用户成功！");

    }
}