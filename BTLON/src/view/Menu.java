package view;


import entities.Account;
import service.AccountService;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public void displayMenu (Scanner scanner, ArrayList<Account> accounts, AccountService service ){
        while (true) {
            System.out.println("====== trang web ======");
            System.out.println("1. dang nhap");
            System.out.println("2. dang ky");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    service.login(scanner, accounts);
                    break;
                case "2":
                    service.creatAccount(scanner, accounts);
                    break;
                default:
                    System.out.println("lua chon sai, chon lai");
            }
        }
    }
}