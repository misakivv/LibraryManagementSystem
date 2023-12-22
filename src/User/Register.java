package User;

import java.sql.*;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Register {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    public static void register() {
        Scanner scanner = new Scanner(System.in);
        // 用户名（唯一）
        System.out.println("请输入用户名（由4-20个字符组成，可以包含字母、数字、下划线）：");
        String username = scanner.nextLine();
        while (true) {
            if (!isValidUserName(username)) {
                System.out.println("用户名格式不正确！请重新输入：");
                username = scanner.nextLine();
            } else {
                try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    if (isUsernameExistsInAdmin(connection, username)) {
                        System.out.println("该用户名为管理员，请重新输入：");
                        username = scanner.nextLine();
                    } else {
                        break;
                    }
                } catch (SQLException e) {
                    System.out.println("数据库查询失败：" + e.getMessage());
                    username = scanner.nextLine();
                }
            }
        }

        // 密码
        System.out.println("请输入密码（由8-16个字符组成，至少包含一个大写字母、一个小写字母和一个数字）：");
        String password = scanner.nextLine();
        while (true) {
            if (!isValidPassWord(password)) {
                System.out.println("密码格式不正确！请重新输入：");
                password = scanner.nextLine();
            } else {
                System.out.println("格式输入正确.");
                break;
            }
        }

        // 身份证号（正则表达式验证）
        System.out.println("请输入身份证号：");
        String idCard = scanner.nextLine();
        while (true) {
            if (!isValidIDCard(idCard)) {
                System.out.println("身份证号格式不正确！请重新输入：");
                idCard = scanner.nextLine();
            } else {
                System.out.println("格式输入正确.");
                break;
            }
        }
        // 手机号码（正则表达式验证）
        System.out.println("请输入手机号码：");
        String phoneNumber = scanner.nextLine();
        while (true) {
            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("手机号码格式不正确！请重新输入：");
                phoneNumber = scanner.nextLine();
            } else {
                System.out.println("格式输入正确.");
                break;
            }
        }
        //验证码
        String random = creatcode.creatCode(4);
        System.out.println("请输入验证码：" + random);
        Scanner in = new Scanner(System.in);
        String code;
        code = in.next();
        while (true) {
            if (code.equals(random)) {
                break;
            } else {
                System.out.println("验证码输入错误！请重新输入：");
                code = scanner.nextLine();
            }
        }


        // 用户编号（假设为自增主键，由数据库自动生成）
        int userId = -1;

        // 借阅数量（默认为0）
        int borrowCount = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createRegisterTable(connection);

            // 插入数据
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO register(username, password, id_card, phone_number, borrow_count) VALUES (?, ?, ?, ?, ?)",
                    new String[]{"userId"});
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, idCard);
            pstmt.setString(4, phoneNumber);
            pstmt.setInt(5, borrowCount);

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
                System.out.println("注册成功，用户编号为：" + userId);
            } else {
                System.out.println("注册失败！");
            }
        } catch (SQLException e) {
            System.out.println("注册失败：用户名已存在");
        }

        //scanner.close();
    }
    //对用户名、密码、身份证号、手机号进行正则表达式过滤
    private static boolean isValidUserName (String username){
        String regex = "[[a-zA-Z|\\d|_]+]{4,20}";
        return Pattern.matches(regex, username);
    }

    private static boolean isValidPassWord (String password){
        String regex = "[[a-zA-Z|\\d]+]{8,16}";
        return Pattern.matches(regex, password);
    }

    private static boolean isValidIDCard (String idCard){
        String regex = "[1-9][0-9]{16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    private static boolean isValidPhoneNumber (String phoneNumber){
        String regex = "^1[3456789]\\d{9}$";
        return Pattern.matches(regex, phoneNumber);
    }

    //创建register表
    private static void createRegisterTable (Connection connection){
        String sql = "CREATE TABLE IF NOT EXISTS register (" +
                "userId INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE," +
                "password VARCHAR(50)," +
                "id_card VARCHAR(20)," +
                "phone_number VARCHAR(11)," +
                "borrow_count INT DEFAULT 0)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("创建register表失败：" + e.getMessage());
        }
    }
    // 检查用户名是否为管理员
    private static void checkAdminUsername(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("用户名为管理员，请重新输入用户名：");
                Scanner in=new Scanner(System.in);
                username=in.next();
            }
        }
    }

    // 检查用户名是否存在于admin表中
    private static boolean isUsernameExistsInAdmin(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

}
