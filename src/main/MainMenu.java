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

                case 2 -> loginUser();

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

        try {
            bank.createUser(username, password, isAdmin, age);

            User user = bank.getUser(username);
            if(!isAdmin){
                user.addAccount(new CustomerAccount(username));
                user.getAccounts().get(username).setAccountType("Normal");
            }
            else{
                user.addAccount(new AdministratorAccount(username, bank));
                user.getAccounts().get(username).setAccountType("Administrator");
            }

            System.out.println("User created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loginUser() {

        System.out.println("\nAvailable users:");

        for (User u : bank.getUsers().values()) {
            System.out.println("- " + u.getUsername());
        }

        System.out.print("Username: ");
        String username = keyboardInput.next();

        System.out.print("Password: ");
        String password = keyboardInput.next();

        User user = bank.getUser(username);

        if (user != null && user.checkPassword(password)) {

            currentUser = user;
            routeToMenu();

        } else {
            System.out.println("Login failed.");
        }
    }

    private void routeToMenu() {

        if (currentUser.isAdmin()) {
            new AdminMenu(currentUser, this).run();
        } else {
            new CustomerMenu(currentUser, this).run();
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