package BorrowRecord;
import BOOK.Book;

import java.sql.*;
import java.util.Scanner;

public class ReturnBook {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public static void returnBooks() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入要归还的图书名称：");
        String bookName = scanner.nextLine();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM book WHERE name = ? AND status = TRUE")) {
            pstmt.setString(1, bookName);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("没有找到已借出的该图书！");
                return;
            }

            Book book = new Book(rs.getString("ISBN"), rs.getString("name"), rs.getString("author"),
                    rs.getString("type"), rs.getInt("remaining_number"), rs.getBoolean("status"));

            System.out.print("请输入要归还的数量：");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > book.getNumber()) {
                System.out.println("超出库存数量！");
                return;
            }

            book.setNumber(book.getNumber() + quantity);
            book.setStatus(false);

            try (PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE book SET remaining_number = ?, status = ? WHERE ISBN = ?")) {
                updateStmt.setInt(1, book.getNumber());
                updateStmt.setBoolean(2, book.isStatus());
                updateStmt.setString(3, book.getISBN());
                updateStmt.executeUpdate();

                System.out.println("归还成功！");
            }
        } catch (SQLException e) {
            System.out.println("还书过程中发生错误: " + e.getMessage());
        }
    }
}



//package BorrowRecord;
//import BOOK.Book;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class ReturnBook {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
//    private static final String USER = "root";
//    private static final String PASSWORD = "123";
//
//    public static void returnBooks() {
//        Connection con = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//        } catch (Exception e) {
//            System.out.println("Error connecting to database: " + e.getMessage());
//            return;
//        }
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("请输入要归还的图书编号：");
//        int bookId = scanner.nextInt();
//        scanner.nextLine();
//
//        try (PreparedStatement pstmt = con.prepareStatement(
//                "SELECT * FROM book WHERE book_id = ? AND status = TRUE")) {
//            pstmt.setInt(1, bookId);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (!rs.next()) {
//                System.out.println("没有找到已借出的该图书！");
//                return;
//            }
//
//            Book book = new Book(rs.getInt("book_id"), rs.getString("ISBN"), rs.getString("name"), rs.getString("author"),
//                    rs.getString("type"), rs.getInt("number"), rs.getInt("remaining_number"), rs.getBoolean("status"));
//
//            System.out.print("请输入要归还的数量：");
//            int quantity = scanner.nextInt();
//            scanner.nextLine();
//
//            if (quantity > book.getRemaining_number()) {
//                System.out.println("超出库存数量！");
//                return;
//            }
//
//            book.setRemaining_number(book.getRemaining_number() + quantity);
//            book.setStatus(false);
//
//            try (PreparedStatement updateStmt = con.prepareStatement(
//                    "UPDATE book SET remaining_number = ?, status = ? WHERE book_id = ?")) {
//                updateStmt.setInt(1, book.getRemaining_number());
//                updateStmt.setBoolean(2, book.isStatus());
//                updateStmt.setInt(3, book.getBookId());
//                updateStmt.executeUpdate();
//
//                System.out.println("归还成功！");
//            }
//        } catch (SQLException e) {
//            System.out.println("还书过程中发生错误: " + e.getMessage());
//        }
//    }
//}