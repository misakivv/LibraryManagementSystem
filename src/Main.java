import BOOK.AddBook;
import BOOK.DelBook;
import BOOK.FindBook;
import BOOK.UpdateBook;
import User.Login;
import User.Register;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        boolean exit = false;
        while (!exit) {
            System.out.println("欢迎使用图书信息管理系统！");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("3. 退出");
            Scanner in=new Scanner(System.in);
            int choice=in.nextInt();
            switch (choice) {
                case 1:
                    Register.register();
                    break;
                case 2:
                    Login.login();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选项，请重新输入！");
                    break;
            }
        }
        System.out.println("感谢使用图书信息管理系统！");
    }



}
