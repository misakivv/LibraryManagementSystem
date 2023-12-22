package User;

import java.sql.*;
import java.util.Scanner;

public class SearchUser {
    public static void searchuser(){
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
            System.out.println("连接失败！！！");
            return;
        }

        Scanner in=new Scanner(System.in);
        System.out.println("请选择查询方式：");
        System.out.println("1. 根据用户名查询");
        System.out.println("2. 根据身份证号查询");
        System.out.println("3. 根据手机号查询");
        System.out.println("4.返回上一级菜单");
        int choice = in.nextInt();

        in.nextLine(); // 添加这一行，消耗掉nextInt()留下的换行符
        boolean exit = false;
        switch (choice) {
            case 1:
                System.out.println("请输入要查询的用户名username：");
                String username = in.nextLine();
                searchByUsername(username, con);
                break;
            case 2:
                System.out.println("请输入要查询的身份证号id_card：");
                String idCard = in.nextLine();
                searchByIdCard(idCard, con);
                break;
            case 3:
                System.out.println("请输入要查询的手机号phone_number：");
                String phoneNumber = in.nextLine();
                searchByPhoneNumber(phoneNumber, con);
                break;
            case 4:
                exit = true;
                break;
            default:
                System.out.println("无效的选择，请重新运行程序并选择正确的查询方式！");
                return;
        }

        //in.close();
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败：" + e.getMessage());
        }
    }

    private static void searchByUsername(String username, Connection con) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM register WHERE username=?");
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("找不到该用户，请检查输入的用户名是否正确！");
                return;
            }

            System.out.println("查询结果如下：");
            System.out.println("用户名： " + resultSet.getString("username"));
            System.out.println("密码： " + resultSet.getString("password"));
            System.out.println("身份证号： " + resultSet.getString("id_card"));
            System.out.println("手机号： " + resultSet.getString("phone_number"));
            System.out.println("借阅次数： " + resultSet.getInt("borrow_count")); // 添加borrow_count字段的输出
            // 如果有其他字段，可以在这里添加相应的输出语句
        } catch (SQLException e) {
            System.out.println("查询失败：" + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("关闭PreparedStatement失败：" + e.getMessage());
            }
        }
    }

    private static void searchByIdCard(String idCard, Connection con) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM register WHERE id_card=?");
            pstmt.setString(1, idCard);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("找不到该用户，请检查输入的身份证号是否正确！");
                return;
            }

            System.out.println("查询结果如下：");
            System.out.println("用户名： " + resultSet.getString("username"));
            System.out.println("密码： " + resultSet.getString("password"));
            System.out.println("身份证号： " + resultSet.getString("id_card"));
            System.out.println("手机号： " + resultSet.getString("phone_number"));
            System.out.println("借阅次数： " + resultSet.getInt("borrow_count")); // 添加borrow_count字段的输出
            // 如果有其他字段，可以在这里添加相应的输出语句
        } catch (SQLException e) {
            System.out.println("查询失败：" + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("关闭PreparedStatement失败：" + e.getMessage());
            }
        }
    }

    private static void searchByPhoneNumber(String phoneNumber, Connection con) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM register WHERE phone_number=?");
            pstmt.setString(1, phoneNumber);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("找不到该用户，请检查输入的手机号是否正确！");
                return;
            }

            System.out.println("查询结果如下：");
            System.out.println("用户名： " + resultSet.getString("username"));
            System.out.println("密码： " + resultSet.getString("password"));
            System.out.println("身份证号： " + resultSet.getString("id_card"));
            System.out.println("手机号： " + resultSet.getString("phone_number"));
            System.out.println("借阅次数： " + resultSet.getInt("borrow_count")); // 添加borrow_count字段的输出
            // 如果有其他字段，可以在这里添加相应的输出语句
        } catch (SQLException e) {
            System.out.println("查询失败：" + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("关闭PreparedStatement失败：" + e.getMessage());
            }
        }
    }
}