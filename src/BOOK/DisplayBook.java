package BOOK;

import java.sql.*;

public class DisplayBook {
    public static void display(){
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
            if(con!=null){
                System.out.println("连接成功！");
            }
        } catch (SQLException e){}
        try {
            sql=con.createStatement();
            rs=sql.executeQuery("select*from book");
            while(rs.next()){
                String name=rs.getString(1);
                String author=rs.getString(2);
                String ISBN=rs.getString(3);
                String type=rs.getString(4);
                System.out.printf("%s\t",name);
                System.out.printf("%s\t",author);
                System.out.printf("%s\t",ISBN);
                System.out.printf("%s\n",type);
            }
            con.close();
        } catch (SQLException e) {}
    }
}
