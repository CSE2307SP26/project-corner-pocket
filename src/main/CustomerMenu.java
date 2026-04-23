package main;

public class CustomerMenu {

    private User currentUser;
    private MainMenu menu;
    private BankAccount currentAccount;

    public CustomerMenu(User currentUser, MainMenu menu) {
        this.currentUser = currentUser;
        this.menu = menu;
    }

    public void run() {

        selectAccount(); 

        int selection = -1;

        while (selection != 12) {

            if (!currentUser.getAccounts().containsValue(currentAccount)) {
                System.out.println("Current account no longer exists. Please select a new one.");
                selectAccount();
            }

            displayMenu();
            selection = menu.getUserSelection(12);
            process(selection);
        }
    }

    private void displayMenu() {

        System.out.println("\nCustomer: " + currentUser.getUsername());
        System.out.println("Active Account: " + currentAccount.getAccountName());

        System.out.println("1. Deposit");
        System.out.println("2. Create account");
        System.out.println("3. Close account");
        System.out.println("4. Withdraw");
        System.out.println("5. Balance");
        System.out.println("6. Transaction history");
        System.out.println("7. Transfer money");
        System.out.println("8. Switch account");
        System.out.println("10. Set password");
        System.out.println("11. Reset password");
        System.out.println("12. Logout");
    }

    private void process(int selection) {

        CustomerAccount acc = (CustomerAccount) currentAccount;

        switch (selection) {

            case 1 -> acc.deposit(askAmount("deposit"));

            case 2 -> createAccount();

            case 3 -> currentUser.removeAccount(acc);

            case 4 -> acc.withdraw(askAmount("withdraw"));

            case 5 -> System.out.println(acc.getBalance());

            case 6 -> acc.getTransactionHistory().forEach(System.out::println);

            case 7 -> transferMoney();

            case 8 -> selectAccount();

            case 10 -> setPassword();

            case 11 -> resetPassword();
        }
    }

    private double askAmount(String type) {
        System.out.print(type + " amount: ");
        return menu.keyboardInput.nextDouble();
    }

    private void transferMoney() {

        System.out.print("Target account: ");
        String toName = menu.keyboardInput.next();

        BankAccount to = currentUser.getAccounts().get(toName);

        if (to == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = menu.keyboardInput.nextDouble();

        menu.bank.transfer(currentAccount, to, amount);
    }

    private void setPassword() {
        System.out.print("New password: ");
        currentUser.setPassword(menu.keyboardInput.next());
    }

    private void resetPassword() {
        currentUser.setPassword(null);
    }

    
    private void createAccount() {

    System.out.print("Account name: ");
    String name = menu.keyboardInput.next();

    try {
        currentUser.createAccount(name, false, menu.bank);
        System.out.println("Account created successfully.");
    } catch (IllegalArgumentException e) {
        System.out.println("Could not create account: " + e.getMessage());
    }
}

    private void selectAccount() {

        while (true) {

            System.out.println("\nYour accounts:");

            for (BankAccount acc : currentUser.getAccounts().values()) {
                System.out.println("- " + acc.getAccountName());
            }

            System.out.print("Select account: ");
            String name = menu.keyboardInput.next();

            BankAccount selected = currentUser.getAccounts().get(name);

            if (selected != null) {
                currentAccount = selected;
                return;
            }

            System.out.println("Account not found. Try again.");
        }
    }
}