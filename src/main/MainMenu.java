package main;

import java.util.Scanner;

public class MainMenu {

    private Scanner keyboardInput;
    private Bank bank;

    private User currentUser = null;
    private BankAccount currentAccount = null;

    private static int EXIT_SELECTION = 11;
    private static int MAX_SELECTION = 11;

    public MainMenu() {
        this.bank = new Bank(10000.00);
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {

        System.out.println("\nWelcome to the 237 Bank App!");

        if (currentUser == null) {

            EXIT_SELECTION = 3;
            MAX_SELECTION = 3;

            System.out.println("Not logged in:");
            System.out.println("1. Create account");
            System.out.println("2. Login (switch user)");
            System.out.println("3. Exit");

        } else if (currentAccount instanceof CustomerAccount) {

            EXIT_SELECTION = 12;
            MAX_SELECTION = 12;

            System.out.println("Customer: " + currentUser.getUsername());
            System.out.println("Active Account: " + currentAccount.getAccountName());

            System.out.println("1. Deposit");
            System.out.println("2. Create account");
            System.out.println("3. Close account");
            System.out.println("4. Withdraw");
            System.out.println("5. Balance");
            System.out.println("6. Transaction history");
            System.out.println("7. Transfer money");
            System.out.println("8. Pay loan");
            System.out.println("9. Switch account");
            System.out.println("10. Set password");
            System.out.println("11. Reset password");
            System.out.println("12. Exit");

        } else {

            EXIT_SELECTION = 9;
            MAX_SELECTION = 9;

            System.out.println("Administrator: " + currentUser.getUsername());
            System.out.println("Active Account: " + currentAccount.getAccountName());

            System.out.println("1. Create account");
            System.out.println("2. Close account");
            System.out.println("3. Collect fees");
            System.out.println("4. Pay interest");
            System.out.println("5. Issue loan");
            System.out.println("6. Switch account");
            System.out.println("7. Set password");
            System.out.println("8. Reset password");
            System.out.println("9. Exit");
        }
    }

    public void processInput(int selection) {

        if (currentUser == null) {

            switch (selection) {
                case 1 -> createAccountAndLogin();
                case 2 -> loginUser();
                case 3 -> System.exit(0);
            }

            return;
        }

        if (currentAccount instanceof CustomerAccount) {
            handleCustomer(selection);
        } else {
            handleAdmin(selection);
        }
    }

    private void handleCustomer(int selection) {

        CustomerAccount acc = (CustomerAccount) currentAccount;

        switch (selection) {

            case 1 -> acc.deposit(askAmount("deposit"));

            case 2 -> createAccount();

            case 3 -> currentUser.removeAccount(acc);

            case 4 -> acc.withdraw(askAmount("withdraw"));

            case 5 -> System.out.println(acc.getBalance());

            case 6 -> acc.getTransactionHistory()
                    .forEach(System.out::println);

            case 7 -> transferMoney();

            case 8 -> acc.payLoan(askAmount("loan payment"));

            case 9 -> switchAccount();

            case 10 -> setPassword();

            case 11 -> resetPassword();

            case 12 -> System.exit(0);
        }
    }

    private void handleAdmin(int selection) {

        AdministratorAccount admin = (AdministratorAccount) currentAccount;

        switch (selection) {

            case 1 -> createAccount();

            case 2 -> closeAccount();

            case 3 -> collectFees(admin);

            case 4 -> payInterest(admin);

            case 5 -> issueLoan(admin);

            case 6 -> switchAccount();

            case 7 -> setPassword();

            case 8 -> resetPassword();

            case 9 -> System.exit(0);
        }
    }

    private void createAccount() {

    System.out.print("Account name: ");
    String name = keyboardInput.next();

    System.out.print("Admin? (true/false): ");
    boolean isAdmin = keyboardInput.nextBoolean();

    if (isAdmin && !currentUser.isAdmin()) {
        System.out.println("Only administrators can create admin accounts.");
        return;
    }

    BankAccount acc;

    if (isAdmin) {
        acc = new AdministratorAccount(name, "", bank.getBankVaultBalance());
    } else {
        acc = new CustomerAccount(name);
    }

    bank.addAccount(acc);
    currentUser.addAccount(acc);
}

 
    private void closeAccount() {
        currentUser.removeAccount(currentAccount);
        bank.closeAccount(currentAccount.getAccountName());
        currentAccount = null;
    }

    private void transferMoney() {

        System.out.print("Target account: ");
        String toName = keyboardInput.next();

        BankAccount to = bank.getAccount(toName);

        if (to == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = keyboardInput.nextDouble();

        ((CustomerAccount) currentAccount).transferMoney(bank, to, amount);
    }

    private void collectFees(AdministratorAccount admin) {

        System.out.print("From account: ");
        String name = keyboardInput.next();

        BankAccount from = bank.getAccount(name);

        System.out.print("Amount: ");
        double amount = keyboardInput.nextDouble();

        admin.collectFees(from, amount);
    }

    private void payInterest(AdministratorAccount admin) {

        System.out.print("Customer account: ");
        String name = keyboardInput.next();

        BankAccount acc = bank.getAccount(name);

        System.out.print("Rate: ");
        int rate = keyboardInput.nextInt();

        if (acc instanceof CustomerAccount c) {
            admin.payInterest(c, rate);
        }
    }

    private void issueLoan(AdministratorAccount admin) {

        System.out.print("Customer account: ");
        String name = keyboardInput.next();

        BankAccount acc = bank.getAccount(name);

        System.out.print("Amount: ");
        double amount = keyboardInput.nextDouble();

        System.out.print("Interest: ");
        int rate = keyboardInput.nextInt();

        if (acc instanceof CustomerAccount c) {
            admin.giveLoan(c, amount, rate);
        }
    }

    private void switchAccount() {

        System.out.println("Your accounts:");

        for (BankAccount acc : currentUser.getAccounts()) {
            System.out.println("- " + acc.getAccountName());
        }

        System.out.print("Select account: ");
        String name = keyboardInput.next();

        for (BankAccount acc : currentUser.getAccounts()) {
            if (acc.getAccountName().equals(name)) {
                currentAccount = acc;
                return;
            }
        }

        System.out.println("Not found.");
    }

    private void loginUser() {

        System.out.print("Username: ");
        String username = keyboardInput.next();

        System.out.print("Password: ");
        String password = keyboardInput.next();

        User user = bank.getUser(username);

        if (user != null && user.checkPassword(password)) {
            currentUser = user;

            if (!user.getAccounts().isEmpty()) {
                currentAccount = user.getAccounts().get(0);
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    private void setPassword() {
        System.out.print("New password: ");
        currentUser.setPassword(keyboardInput.next());
    }

    private void resetPassword() {
        currentUser.setPassword(null);
    }


    private double askAmount(String type) {
        System.out.print(type + " amount: ");
        return keyboardInput.nextDouble();
    }

    public void run() {
        int selection = -1;

        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public int getUserSelection(int max) {
        int sel = -1;
        while (sel < 1 || sel > max) {
            System.out.print("Select: ");
            sel = keyboardInput.nextInt();
        }
        return sel;
    }

    public static void main(String[] args) {
        new MainMenu().run();
    }
}