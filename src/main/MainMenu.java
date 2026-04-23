package main;

import java.util.Scanner;

public class MainMenu {

    Scanner keyboardInput;
    Bank bank;

    User currentUser = null;

    public MainMenu() {
        this.bank = new Bank(10000.00);
        this.keyboardInput = new Scanner(System.in);
    }

    public void run() {

        int selection = -1;

        while (selection != 3) {

            System.out.println("\nWelcome to the 237 Bank App!");
            System.out.println("1. Create user");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            selection = getUserSelection(3);

            switch (selection) {

                case 1 -> createUser();

                case 2 -> {
                    loginUser();

                    if (currentUser != null) {
                        routeToMenu();

                        // logout after submenu finishes
                        currentUser = null;
                    }
                }

                case 3 -> System.exit(0);
            }
        }
    }

    private void createUser() {

        System.out.print("Username: ");
        String username = keyboardInput.next();

        System.out.print("Password: ");
        String password = keyboardInput.next();

        System.out.print("Age: ");
        int age = keyboardInput.nextInt();

        if (age < 18) {
            System.out.println("You must be 18 or older to create an account.");
            return;
        }

        System.out.print("Admin? (true/false): ");
        boolean isAdmin = keyboardInput.nextBoolean();

        if (isAdmin) {
            System.out.println("Admin users must be created by an existing administrator.");
            return;
        }

        try {
            bank.createUser(username, password, false, age);
            System.out.println("User created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loginUser() {

        System.out.print("Username: ");
        String username = keyboardInput.next();

        System.out.print("Password: ");
        String password = keyboardInput.next();

        User user = bank.getUser(username);

        if (user != null && user.checkPassword(password)) {
            currentUser = user;
        } else {
            System.out.println("Login failed.");
        }
    }

    private void routeToMenu() {

        if (currentUser == null) return;

        if (currentUser.getAccounts().isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }

        BankAccount firstAccount =
                currentUser.getAccounts().values().iterator().next();

        if (firstAccount instanceof CustomerAccount) {
            new CustomerMenu(currentUser, this).run();
        } else {
            new AdminMenu(currentUser, this).run();
        }
    }

    int getUserSelection(int max) {

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