package User;

import BOOK.*;
import BorrowRecord.BorrowBook;
import BorrowRecord.ReturnBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static final int MAX_ATTEMPTS = 3;

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            // 获取用户名和密码
            System.out.println("请输入用户名：");
            String username = scanner.nextLine();
            System.out.println("请输入密码：");
            String password1 = scanner.nextLine();
            System.out.println("请再次输入密码：");
            String password2 = scanner.nextLine();
            while(true) {
                if (password2.equals(password1)) {
                    System.out.println("两次密码输入一致.");
                    break;
                }
                else{
                    System.out.println("两次密码输入不一致！");
                    System.out.println("请再次输入密码：");
                    password2 = scanner.nextLine();
                }
            }
            String random= creatcode.creatCode(4);
            System.out.println("请输入验证码："+random);
            Scanner in=new Scanner(System.in);
            String code;
            code=in.next();
            while(true) {
                if (code.equals(random)) {
                    break;
                }
                else{
                    System.out.println("验证码输入错误！请重新输入：");
                    code = scanner.nextLine();
                }
            }


            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                if (authenticateUserInBothTables(connection, username, password1)) {
                    System.out.println("登录成功！");
                    int admin=1;
                    int normal=0;
                    int role;
                    System.out.println("请输入你的身份（admin:1,normal:0)：");
                    Scanner read=new Scanner(System.in);
                    role=read.nextInt();
                    if(role==1) {
                        boolean exit = false;

                        while (!exit) {
                            System.out.println("欢迎，admin--" + username + "！");
                            System.out.println("1. 用户管理");
                            System.out.println("2. 图书信息管理");
                            System.out.println("3. 借还书功能");
                            System.out.println("4. 退出登录");
                            int choice=in.nextInt();
                            switch (choice) {
                                case 1:
                                    manageUsers();
                                    break;
                                case 2:
                                    manageBooks();
                                    break;
                                case 3:
                                    borrowReturnBook();
                                    break;
                                case 4:
                                    exit = true;
                                    username = null;
                                    break;
                                default:
                                    System.out.println("无效的选项，请重新输入！");
                                    break;
                            }
                        }
                    }
                    if(role==0){
                        System.out.println("============================");
                        System.out.println("欢迎" + username + "使用图书管理系统");
                        System.out.println("      1.借阅图书     ");
                        System.out.println("      2.归还图书     ");
                        System.out.println("      3.查找图书     ");
                        System.out.println("      4.退出系统     ");
                        System.out.println("============================");
                        System.out.println("请输入你的选择：");
                        Scanner read2=new Scanner(System.in);
                        int b=read2.nextInt();
                        if(b==1){
                            BorrowBook.borrow();
                        }
                        else if(b==2){
                            ReturnBook.returnBooks();
                        }
                        else if(b==3){
                            FindBook.find();
                        }
                        else {
                            System.exit(-1);
                        }
                    }
                    // ...
                    return; // 登录成功，退出循环
                } else {
                    boolean isUsernameError = isUsernameIncorrectInBothTables(connection, username);
                    if (isUsernameError) {
                        System.out.println("用户名不存在，请检查您的输入！");
                    } else {
                        System.out.println("密码错误，请重新输入！");
                    }
                    attempts++;
                }
            } catch (SQLException e) {
                System.out.println("登录失败：" + e.getMessage());
                attempts++;
            }
        }

        System.out.println("您已连续输错密码超过三次，程序将结束运行。");
        System.exit(0); // 超过最大尝试次数，结束程序
    }

//    private static boolean authenticateUser(Connection connection, String username, String password) throws SQLException {
//        String sql = "SELECT * FROM register WHERE BINARY username = ? AND BINARY password = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
//            ResultSet resultSet = pstmt.executeQuery();
//
//            return resultSet.next(); // 用户名和密码正确则返回true，否则返回false
//        }
//    }

    private static boolean authenticateUserInBothTables(Connection connection, String username, String password) throws SQLException {
        String sqlRegister = "SELECT * FROM register WHERE BINARY username = ? AND BINARY password = ?";
        String sqlAdmin = "SELECT * FROM admin WHERE username = ? AND BINARY password = ?";

        try (PreparedStatement pstmtRegister = connection.prepareStatement(sqlRegister);
             PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)) {

            pstmtRegister.setString(1, username);
            pstmtRegister.setString(2, password);
            ResultSet resultSetRegister = pstmtRegister.executeQuery();

            pstmtAdmin.setString(1, username);
            pstmtAdmin.setString(2, password);
            ResultSet resultSetAdmin = pstmtAdmin.executeQuery();

            return resultSetRegister.next() || resultSetAdmin.next(); // 如果在任一表中找到匹配的用户名和密码，则返回true，否则返回false
        }
    }

//    private static boolean authenticateUserInBothTables(Connection connection, String username, String password) throws SQLException {
//        String sqlRegister = "SELECT * FROM register WHERE BINARY username = ? AND BINARY password = ?";
//        String sqlAdmin = "SELECT * FROM admin WHERE username = ? AND BINARY password = ?";
//
//        try (PreparedStatement pstmtRegister = connection.prepareStatement(sqlRegister);
//             PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)) {
//
//            pstmtRegister.setString(1, username);
//            pstmtRegister.setString(2, password);
//            ResultSet resultSetRegister = pstmtRegister.executeQuery();
//
//            pstmtAdmin.setString(1, username);
//            pstmtAdmin.setString(2, password);
//            ResultSet resultSetAdmin = pstmtAdmin.executeQuery();
//
//            return resultSetRegister.next() || resultSetAdmin.next(); // 如果在任一表中找到匹配的用户名和密码，则返回true，否则返回false
//        }
//    }
//
//    private static boolean isUsernameIncorrect(Connection connection, String username) throws SQLException {
//        String sqlRegister = "SELECT COUNT(*) FROM register WHERE BINARY username = ?";
//        String sqlAdmin = "SELECT COUNT(*) FROM admin WHERE username = ?";
//
//        try (PreparedStatement pstmtRegister = connection.prepareStatement(sqlRegister);
//             PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)) {
//
//            pstmtRegister.setString(1, username);
//            ResultSet resultSetRegister = pstmtRegister.executeQuery();
//            resultSetRegister.next();
//            int countRegister = resultSetRegister.getInt(1);
//
//            pstmtAdmin.setString(1, username);
//            ResultSet resultSetAdmin = pstmtAdmin.executeQuery();
//            resultSetAdmin.next();
//            int countAdmin = resultSetAdmin.getInt(1);
//
//            return countRegister == 0 && countAdmin == 0; // 如果在两个表中都找不到用户名，则返回true，否则返回false
//        }
//    }

    private static boolean isUsernameIncorrectInBothTables(Connection connection, String username) throws SQLException {
        String sqlRegister = "SELECT COUNT(*) FROM register WHERE BINARY username = ?";
        String sqlAdmin = "SELECT COUNT(*) FROM admin WHERE username = ?";

        try (PreparedStatement pstmtRegister = connection.prepareStatement(sqlRegister);
             PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)) {

            pstmtRegister.setString(1, username);
            ResultSet resultSetRegister = pstmtRegister.executeQuery();
            resultSetRegister.next();
            int countRegister = resultSetRegister.getInt(1);

            pstmtAdmin.setString(1, username);
            ResultSet resultSetAdmin = pstmtAdmin.executeQuery();
            resultSetAdmin.next();
            int countAdmin = resultSetAdmin.getInt(1);

            return countRegister == 0 && countAdmin == 0; // 如果在两个表中都找不到用户名，则返回true，否则返回false
        }
    }

    private static void manageUsers() {
        // 实现用户管理模块的代码
        boolean exit = false;
        System.out.println("用户管理模块");
        System.out.println("1. 添加用户");
        System.out.println("2. 删除用户");
        System.out.println("3. 修改用户信息");
        System.out.println("4. 查询用户");
        System.out.println("5. 返回上级菜单");
        Scanner in=new Scanner(System.in);
        int choice=in.nextInt();

        switch (choice) {
            case 1:
                AddUser.AddUser();
                break;
            case 2:
                DelUser.deluser();
                break;
            case 3:
                AlterUser.alteruser();
                break;
            case 4:
                SearchUser.searchuser();
                break;
            case 5:
                exit = true;
                break;
            default:
                System.out.println("无效的选项，请重新输入！");
                break;
        }
    }
    private static void manageBooks() {
        boolean exit = false;

        while (!exit) {
            System.out.println("图书信息管理");
            System.out.println("1. 添加图书");
            System.out.println("2. 删除图书");
            System.out.println("3. 修改图书信息");
            System.out.println("4. 查询图书");
            System.out.println("5. 返回上级菜单");
            Scanner in=new Scanner(System.in);
            int choice=in.nextInt();

            switch (choice) {
                case 1:
                    AddBook.addbook();
                    break;
                case 2:
                    DelBook.delbook();
                    break;
                case 3:
                    UpdateBook.update();
                    break;
                case 4:
                    FindBook.find();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选项，请重新输入！");
                    break;
            }
        }
    }
    private static void borrowReturnBook() {
        // 实现借还书功能模块的代码
        boolean exit = false;
        while (!exit) {
            System.out.println("借还书功能模块");
            System.out.println("1. 借阅图书");
            System.out.println("2. 归还图书");
            System.out.println("3. 返回上级菜单");
            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    BorrowBook.borrow();
                    break;
                case 2:
                    ReturnBook.returnBooks();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选项，请重新输入！");
                    break;
            }
        }
    }
}