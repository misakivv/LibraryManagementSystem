package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddUser {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "123";

    public static void AddUser() {
        Scanner scanner = new Scanner(System.in);

        String username = "";
        System.out.println("添加新用户：");
        while (true) {
            System.out.print("请输入用户名(由4-20个字符组成，可以包含字母、数字、下划线)：");
            username = scanner.nextLine();
            if (isValidUserName(username)) {
                break;
            } else {
                System.out.println("用户名格式不正确，请重新输入！");
            }
        }

        String password = "";
        while (true) {
            System.out.print("请输入密码（由8-16个字符组成，至少包含一个大写字母、一个小写字母和一个数字）：");
            String newPassword = scanner.nextLine();
            if (isValidPassWord(newPassword)) {
                System.out.print("请再次输入密码进行确认：");
                String confirmPassword = scanner.nextLine();
                if (newPassword.equals(confirmPassword)) {
                    password = newPassword;
                    break;
                } else {
                    System.out.println("两次输入的密码不一致，请重新输入！");
                }
            } else {
                System.out.println("密码格式不正确，请重新输入！");
            }
        }

        String idCard = "";
        while (true) {
            System.out.print("请输入身份证号码：");
            idCard = scanner.nextLine();
            if (isValidIDCard(idCard)) {
                break;
            } else {
                System.out.println("身份证号码格式不正确，请重新输入！");
            }
        }

        String phoneNumber = "";
        while (true) {
            System.out.print("请输入电话号码：");
            phoneNumber = scanner.nextLine();
            if (isValidPhoneNumber(phoneNumber)) {
                break;
            } else {
                System.out.println("电话号码格式不正确，请重新输入！");
            }
        }

        System.out.print("请输入借书次数（默认为0，最大值为5）：");
        int borrowCount = scanner.nextInt();
        scanner.nextLine(); // 消耗掉换行符

        if (borrowCount > 5) {
            System.out.println("借书次数不能超过5次，请重新输入！");
            borrowCount = scanner.nextInt();
            scanner.nextLine(); // 消耗掉换行符
        }

        try {
            // 加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 创建数据库连接
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            // 创建SQL语句
            String sql = "INSERT INTO register (username, password, id_card, phone_number, borrow_count) VALUES (?, ?, ?, ?, ?)";

            // 创建PreparedStatement对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // 设置参数
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, idCard);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, borrowCount);

            // 执行SQL语句
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("用户添加成功！");
            } else {
                System.out.println("用户添加失败！");
            }

            // 关闭资源
            preparedStatement.close();
            connection.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("该用户已存在，无法添加！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加用户过程中发生错误，请稍后重试！");
        }
    }

    private static boolean isValidUserName(String username) {
        String regex = "[a-zA-Z\\d_]{4,20}";
        return Pattern.matches(regex, username);
    }

    private static boolean isValidPassWord(String password) {
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}";
        return Pattern.matches(regex, password);
    }

    private static boolean isValidIDCard(String idCard) {
        String regex = "[1-9][0-9]{16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^1[3456789]\\d{9}$";
        return Pattern.matches(regex, phoneNumber);
    }
}