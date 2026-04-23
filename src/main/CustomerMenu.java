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
                System.out.println("Account no longer exists. Please select a new one.");
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
        System.out.println("9. Pay loan");
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

            case 4 -> withdraw(acc);

            case 5 -> System.out.println(acc.getBalance());

            case 6 -> acc.getTransactionHistory().forEach(System.out::println);

            case 7 -> transferMoney();

            case 8 -> selectAccount();

            case 9 -> payLoan(acc);

            case 10 -> setPassword();

            case 11 -> resetPassword();

            case 12 -> System.out.println("Logging out...");
        }
    }

    private void payLoan(CustomerAccount acc) {

        System.out.print("Amount to pay towards loan: ");
        double amount = menu.keyboardInput.nextDouble();

        try {
            acc.payLoan(amount);
            System.out.println("Loan payment successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not process loan payment: " + e.getMessage());
        }
    }

    private void withdraw(CustomerAccount acc) {

    double amount = askAmount("withdraw");

    try {
        menu.bank.withdraw(acc, currentUser, amount);
        System.out.println("Withdrawal successful.");
    } catch (IllegalArgumentException e) {
        System.out.println("Withdrawal failed: " + e.getMessage());
    }
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

        try {
            menu.bank.transfer(currentAccount, to, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private void createAccount() {

        System.out.print("Account name: ");
        String name = menu.keyboardInput.next();

        System.out.print("Account Type (Educational Account / Investment Account / Normal Account): ");
        String type = menu.keyboardInput.next();

        try {
            currentUser.createAccount(name, false, menu.bank, type);
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create account: " + e.getMessage());
        }
    }

    private void setPassword() {
        System.out.print("New password: ");
        currentUser.setPassword(menu.keyboardInput.next());
    }

    private void resetPassword() {
        currentUser.setPassword(null);
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

    private double askAmount(String type) {
        System.out.print(type + " amount: ");
        return menu.keyboardInput.nextDouble();
    }
}