package BOOK;
import java.sql.*;
import java.util.Scanner;

public class UpdateBook {
    public static void update() {
        Connection con = null;
        Statement sql = null;
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
        } catch (
                SQLException e) {
        }
        if (con == null) {
            System.out.printf("连接失败！！！");
            return;
        }

        Scanner in = new Scanner(System.in);
        System.out.println("请输入要修改的书名name：");
        String name = in.nextLine();

        // 获取图书当前的信息
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM book WHERE name=?");
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("找不到该图书，请检查输入的书名是否正确！");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 提示用户输入新的信息
        System.out.println("请输入新的作者（留空则不修改）：");
        String newAuthor = in.nextLine();
        if (newAuthor.isEmpty()) {
            System.out.println("作者未修改！");
        } else {
            System.out.println("请输入确认新作者：");
            String Author = in.nextLine();
            while (true) {
                if (!newAuthor.equals(Author)) {
                    System.out.println("两次输入的新作者不一致，请重新输入新作者！");
                    Author = in.nextLine();
                } else {
                    System.out.println("作者修改成功！");
                    break;
                }
            }
        }
        System.out.println("请输入新的ISBN（留空则不修改）：");
        String newISBN = in.nextLine();
        if (newISBN.isEmpty()) {
            System.out.println("ISBN未修改！");
        } else {
            System.out.println("请输入确认新ISBN：");
            String confirmISBN = in.nextLine();
            while(true) {
                if (!newISBN.equals(confirmISBN)) {
                    System.out.println("两次输入的新ISBN不一致，请重新输入新ISBN！");
                    confirmISBN = in.nextLine();
                }
                else{
                    System.out.println("ISBN修改成功！");
                    break;
                }
            }
        }

        System.out.println("请输入新的type（留空则不修改）：");
        String newtype = in.nextLine();
        if (newtype.isEmpty()) {
            System.out.println("type未修改！");
        } else {
            System.out.println("请输入确认新type：");
            String confirmtype = in.nextLine();
            while(true) {
                if (!newtype.equals(confirmtype)) {
                    System.out.println("两次输入的新手机号不一致，请重新输入新type！");
                    confirmtype = in.nextLine();
                }
                else{
                    System.out.println("type修改成功！");
                    break;
                }
            }
        }

        // 更新图书信息
        pstmt = null;
        try {
            String updateQuery = "UPDATE book SET ";
            if (!newAuthor.isEmpty()) {
                updateQuery += "author=?, ";
            }
            if (!newISBN.isEmpty()) {
                updateQuery += "ISBN=?, ";
            }
            if (!newtype.isEmpty()) {
                updateQuery += "type=? ";
            }
            updateQuery = updateQuery.trim().substring(0, updateQuery.length() - 1) + " WHERE name=?";

            pstmt = con.prepareStatement(updateQuery);
            int index = 1;
            if (!newAuthor.isEmpty()) {
                pstmt.setString(index++, newAuthor);
            }
            if (!newISBN.isEmpty()) {
                pstmt.setString(index++, newISBN);
            }
            if (!newtype.isEmpty()) {
                pstmt.setString(index++, newtype);
            }
            pstmt.setString(index++, name);

            pstmt.executeUpdate();
            System.out.println("图书信息修改成功！");
        } catch (SQLException e) {

        }
    }
}
