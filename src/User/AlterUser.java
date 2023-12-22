package User;

import java.sql.*;
import java.util.Scanner;

public class AlterUser {
    public static void alteruser(){
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
        System.out.println("请输入要修改的用户的用户名username：");
        String username = in.nextLine();

        // 获取用户当前的信息
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM register WHERE username=?");
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("找不到该用户，请检查输入的用户名是否正确！");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 提示用户输入新的信息
        System.out.println("请输入新的密码password（留空则不修改）：");
        String newPassword = in.nextLine();
        if (newPassword.isEmpty()) {
            System.out.println("密码未修改！");
        } else {
            System.out.println("请输入确认新密码：");
            String confirmPassword = in.nextLine();
            while(true) {
                if (!newPassword.equals(confirmPassword)) {
                    System.out.println("两次输入的新密码不一致，请重新输入新密码！");
                    confirmPassword = in.nextLine();
                }
                else{
                    System.out.println("密码修改成功！");
                    break;
                }
            }
        }
        System.out.println("请输入新的身份证号id_card（留空则不修改）：");
        String newid_card = in.nextLine();
        if (newid_card.isEmpty()) {
            System.out.println("身份证号未修改！");
        } else {
            System.out.println("请输入确认新身份证号：");
            String confirmIdentify = in.nextLine();
            while(true) {
                if (!newid_card.equals(confirmIdentify)) {
                    System.out.println("两次输入的新身份证号不一致，请重新输入新身份证号！");
                    confirmIdentify = in.nextLine();
                }
                else{
                    System.out.println("身份证号修改成功！");
                    break;
                }
            }
        }

        System.out.println("请输入新的手机号phone_number（留空则不修改）：");
        String newphone_number = in.nextLine();
        if (newphone_number.isEmpty()) {
            System.out.println("手机号未修改！");
        } else {
            System.out.println("请输入确认新手机号：");
            String confirmphone_number = in.nextLine();
            while(true) {
                if (!newphone_number.equals(confirmphone_number)) {
                    System.out.println("两次输入的新手机号不一致，请重新输入新手机号！");
                    confirmphone_number = in.nextLine();
                }
                else{
                    System.out.println("手机号修改成功！");
                    break;
                }
            }
        }



        // 更新用户信息
        pstmt = null;
        try {
            String updateQuery = "UPDATE register SET ";
            if (!newPassword.isEmpty()) {
                updateQuery += "password=?, ";
            }
            if (!newid_card.isEmpty()) {
                updateQuery += "id_card=?, ";
            }
            if (!newphone_number.isEmpty()) {
                updateQuery += "phone_number=? ";
            }
            updateQuery = updateQuery.trim().substring(0, updateQuery.length() - 1) + " WHERE username=?";

            pstmt = con.prepareStatement(updateQuery);
            int index = 1;
            if (!newPassword.isEmpty()) {
                pstmt.setString(index++, newPassword);
            }
            if (!newid_card.isEmpty()) {
                pstmt.setString(index++, newid_card);
            }
            if (!newphone_number.isEmpty()) {
                pstmt.setString(index++, newphone_number);
            }
            pstmt.setString(index++, username);

            pstmt.executeUpdate();
            System.out.println("用户信息修改成功！");
        } catch (SQLException e) {
        }
    }
}