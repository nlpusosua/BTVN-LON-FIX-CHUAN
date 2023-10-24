package service;


import entities.Account;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountService {
    public static boolean emailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean passwordValid(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[\\W_]).{7,15}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void login(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("nhap username: ");
        String userName = scanner.nextLine();
        System.out.println("nhap password: ");
        String passWord = scanner.nextLine();
        Account findAccount = findAccountUsername(userName, accounts);
        if (findAccount != null) {
            if (findAccount.getPassWord().equals(passWord)){
                System.out.println("dang nhap thanh cong, ban co the lam cac dieu sau");
                while (true) {
                    System.out.println("1. thay doi username");
                    System.out.println("2. thay doi email");
                    System.out.println("3. thay doi password");
                    System.out.println("4. dang xuat");
                    System.out.println( "0. thoat chuong trinh");
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            changeUsername(scanner, accounts, findAccount);
                            break;
                        case "2":
                            changeEmail(scanner, accounts, findAccount);
                            break;
                        case "3":
                            changePassword(scanner, findAccount);
                            break;
                        case "4":
                            return;
                        case "0":
                            System.out.println("chuong trinh da thoat");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Lua chon sai, chon lai");
                    }
                }
            } else {
                wrongPassword(scanner, accounts);
            }
        } else {
            System.out.println("tk ko ton tai, kiem tra lai");
        }
    }

    private Account findAccountEmail(String email, ArrayList<Account> accounts) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

    private Account findAccountUsername(String userName, ArrayList<Account> accounts) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                return account;
            }
        }
        return null;
    }

    private void wrongPassword(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("sai mk");
        System.out.println("1. dnag nhap lai");
        System.out.println("2. quen mat khau");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                login(scanner, accounts);
                break;
            case "2":
                forgetPassword(scanner,accounts);
                break;
            default:
                System.out.println("lua chon sai");
        }
    }

    private boolean userNameExit(String newUsername, ArrayList<Account> accounts) {
        for (Account existUser : accounts) {
            if (existUser.getUserName().equals(newUsername)) {
                return false;
            }
        }
        return true;
    }

    private boolean emailExit(String newEmail, ArrayList<Account> accounts, Account account) {
        for (Account existUser : accounts) {
            if (existUser != account && existUser.getEmail().equals(newEmail)) {
                return false;
            }
        }
        return true;
    }

    public void changeUsername(Scanner scanner, ArrayList<Account> accounts, Account account) {
        System.out.println("nhap username moi");
        String newUsername = scanner.nextLine();
        if (userNameExit(newUsername, accounts)) {
            account.setUserName(newUsername);
            System.out.println("username da dc doi");
        } else {
            System.out.println("username da ton tai");
            changeUsername(scanner, accounts, account);
        }
    }

    private void changeEmail(Scanner scanner, ArrayList<Account> accounts, Account account) {
        System.out.println("nhap email moi");
        String newEmail = scanner.nextLine();
        if (emailValid(newEmail)) {
            if (emailExit(newEmail, accounts, account)) {
                account.setEmail(newEmail);
                System.out.println("email da dc doi");
            } else {
                System.out.println("email da ton tai");
                changeEmail(scanner, accounts, account);
            }
        } else {
            System.out.println("email ko hop le");
            changeEmail(scanner, accounts, account);
        }
    }

    public void changePassword(Scanner scanner,Account account) {
        System.out.println("nhap pass moi (dai tu 7-15 kt co chu in hoa va 1 kt dac biet");
        String newPassword = scanner.nextLine();
        if (passwordValid(newPassword)) {
            account.setPassWord(newPassword);
            System.out.println("pass doi thanh cong");
        } else {
            System.out.println("pass ko hop le");
            changePassword(scanner, account);
        }
    }

    public void forgetPassword(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("nhap username");
        String username = scanner.nextLine();

        Account account = findAccountUsername(username, accounts);
        if (account != null) {
            System.out.println("nhap email da dang ky");
            String registeredEmail = scanner.nextLine();

            if (registeredEmail.equals(account.getEmail())) {
                System.out.println("nhap pass moi (dai tu 7-15 kt co chu in hoa va 1 kt dac biet)");
                String newPassword = scanner.nextLine();

                if (passwordValid(newPassword)) {
                    account.setPassWord(newPassword);
                    System.out.println("pass da dc doi");
                    login(scanner, accounts);
                } else {
                    System.out.println("pass ko hop le");
                    forgetPassword(scanner, accounts);
                }
            } else {
                System.out.println("Email khong khop voi tai khoan");
                forgetPassword(scanner, accounts);
            }
        } else {
            System.out.println("Username khong ton tai");
            forgetPassword(scanner, accounts);
        }
    }

    public void creatAccount(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("nhap username");
        String userName = scanner.nextLine();
        if (userNameExit(userName, accounts)) {
            System.out.println("Nhập Email");
            String email = scanner.nextLine();
            if (emailExit(email, accounts, null) && emailValid(email)) {
                System.out.println("nhap pass moi (dai tu 7-15 kt co chu in hoa va 1 kt dac biet)");
                String passWord = scanner.nextLine();
                if (passwordValid(passWord)) {
                    Account account= new Account(userName, email, passWord);
                    accounts.add(account);
                    System.out.println("tk tao thanh cong");
                } else {
                    System.out.println("pass ko hop le");
                }
            } else {
                if (!emailValid(email)) {
                    System.out.println("email ko hop le");
                } else {
                    System.out.println("email da ton tai");
                }
            }
        } else {
            System.out.println("Username da ton tai");
        }
    }


}


