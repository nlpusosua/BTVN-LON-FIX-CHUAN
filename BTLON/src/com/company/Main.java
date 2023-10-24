package com.company;


import entities.Account;
import service.AccountService;
import view.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts= new ArrayList<>();
        Menu menu = new Menu();
        AccountService service = new AccountService();
        menu.displayMenu(scanner, accounts, service);
    }
}
