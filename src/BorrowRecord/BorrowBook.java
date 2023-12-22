package BorrowRecord;
import BOOK.Book;
import java.time.LocalDate;
import java.sql.*;
import java.util.Scanner;

public class BorrowBook {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public static void borrow() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入要借阅的图书名称：");
        String bookName = scanner.nextLine();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM book WHERE name = ? AND status = FALSE")) {
            pstmt.setString(1, bookName);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("没有找到可供借阅的该图书！");
                return;
            }

            Book book = new Book(rs.getString("ISBN"), rs.getString("name"), rs.getString("author"),
                    rs.getString("type"), rs.getInt("remaining_number"), rs.getBoolean("status"));

            System.out.print("请输入要借阅的数量（最多5本）：");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 5 || quantity > book.getNumber()) {
                System.out.println("超出最大借阅数量或库存不足！");
                return;
            }

            book.setNumber(book.getNumber() - quantity);
            book.setStatus(true);

            try (PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE book SET remaining_number = ?, status = ? WHERE ISBN = ?")) {
                updateStmt.setInt(1, book.getNumber());
                updateStmt.setBoolean(2, book.isStatus());
                updateStmt.setString(3, book.getISBN());
                updateStmt.executeUpdate();

//                System.out.println("借阅成功！");
                System.out.println("借" + bookName + "成功！");
                LocalDate dateone=LocalDate.now();
                System.out.println("借书时间："+dateone);
                LocalDate dateafter=dateone.plusWeeks(2);
                System.out.println("请在"+dateafter+"前归还");
            }
        } catch (SQLException e) {
            System.out.println("借书过程中发生错误: " + e.getMessage());
        }
    }
}





//package BorrowRecord;
//import BOOK.Book;
//import java.time.LocalDate;
//import java.sql.*;
//import java.util.Scanner;
//
//public class BorrowBook {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
//    private static final String USER = "root";
//    private static final String PASSWORD = "123";
//
//    public static void borrow() {
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
//        System.out.print("请输入要借阅的图书编号：");
//        int bookId = scanner.nextInt();
//        scanner.nextLine();
//
//        try (PreparedStatement pstmt = con.prepareStatement(
//                "SELECT * FROM book WHERE book_id = ? AND status = FALSE")) {
//            pstmt.setInt(1, bookId);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (!rs.next()) {
//                System.out.println("没有找到可供借阅的该图书！");
//                return;
//            }
//
//            Book book = new Book(rs.getInt("book_id"), rs.getString("ISBN"), rs.getString("name"), rs.getString("author"),
//                    rs.getString("type"), rs.getInt("number"), rs.getInt("remaining_number"), rs.getBoolean("status"));
//
//            System.out.print("请输入要借阅的数量（最多5本）：");
//            int quantity = scanner.nextInt();
//            scanner.nextLine();
//
//            if (quantity > 5 || quantity > book.getRemaining_number()) {
//                System.out.println("超出最大借阅数量或库存不足！");
//                return;
//            }
//
//            book.setRemaining_number(book.getRemaining_number() - quantity);
//            book.setStatus(true);
//
//            try (PreparedStatement updateStmt = con.prepareStatement(
//                    "UPDATE book SET remaining_number = ?, status = ? WHERE book_id = ?")) {
//                updateStmt.setInt(1, book.getRemaining_number());
//                updateStmt.setBoolean(2, book.isStatus());
//                updateStmt.setInt(3, book.getBookId());
//                updateStmt.executeUpdate();
//
////                System.out.println("借阅成功！");
//                System.out.println("借" + book.getName() + "成功！");
//                LocalDate dateone=LocalDate.now();
//                System.out.println("借书时间："+dateone);
//                LocalDate dateafter=dateone.plusWeeks(2);
//                System.out.println("请在"+dateafter+"前归还");
//            }
//        } catch (SQLException e) {
//            System.out.println("借书过程中发生错误: " + e.getMessage());
//        }
//    }
//}