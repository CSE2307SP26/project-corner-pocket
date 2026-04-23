package main;

public class AdminMenu {

    private User currentUser;
    private MainMenu menu;
    private BankAccount currentAccount;

    public AdminMenu(User currentUser, MainMenu menu) {
        this.currentUser = currentUser;
        this.menu = menu;
    }

    public void run() {

        selectAccount();

        int selection = -1;

        while (selection != 9) {

            displayMenu();
            selection = menu.getUserSelection(9);
            process(selection);
        }
    }

    private void displayMenu() {

        System.out.println("\nAdministrator: " + currentUser.getUsername());
        System.out.println("Active Account: " + currentAccount.getAccountName());

        System.out.println("1. Create account");
        System.out.println("2. Close account");
        System.out.println("3. Collect fees");
        System.out.println("4. Pay interest");
        System.out.println("5. Issue loan");
        System.out.println("6. Switch account");
        System.out.println("7. Set password");
        System.out.println("8. Reset password");
        System.out.println("9. Logout");
    }

    private void process(int selection) {

        AdministratorAccount admin = (AdministratorAccount) currentAccount;

        switch (selection) {

            case 1 -> createAccount();

            case 2 -> closeAccount();

            case 3 -> collectFees(admin);

            case 4 -> payInterest(admin);

            case 5 -> issueLoan(admin);

            case 6 -> selectAccount();

            case 7 -> setPassword();

            case 8 -> resetPassword();
        }
    }

    private void createAccount() {

        System.out.print("Account name: ");
        String name = menu.keyboardInput.next();

        System.out.print("Admin account? (true/false): ");
        boolean isAdmin = menu.keyboardInput.nextBoolean();

        try {
            currentUser.createAccount(name, isAdmin, menu.bank, "Administrator Account");
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create account: " + e.getMessage());
        }
    }

    private void closeAccount() {
        currentUser.removeAccount(currentAccount);
        currentAccount = null;
    }

    private void collectFees(AdministratorAccount admin) {

        System.out.print("From account: ");
        String name = menu.keyboardInput.next();

        BankAccount target = currentUser.getAccounts().get(name);

        if (!(target instanceof CustomerAccount account)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Amount: ");
        double amount = menu.keyboardInput.nextDouble();

        admin.collectFees(account, amount);
    }

    private void payInterest(AdministratorAccount admin) {

        System.out.print("Customer account: ");
        String name = menu.keyboardInput.next();

        BankAccount target = currentUser.getAccounts().get(name);

        if (!(target instanceof CustomerAccount account)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Rate: ");
        int rate = menu.keyboardInput.nextInt();

        admin.payInterest(account, rate);
    }

    private void issueLoan(AdministratorAccount admin) {

        System.out.print("Customer account: ");
        String name = menu.keyboardInput.next();

        BankAccount target = currentUser.getAccounts().get(name);

        if (!(target instanceof CustomerAccount account)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Amount: ");
        double amount = menu.keyboardInput.nextDouble();

        System.out.print("Interest rate: ");
        int rate = menu.keyboardInput.nextInt();

        admin.giveLoan(account, amount, rate);
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
}