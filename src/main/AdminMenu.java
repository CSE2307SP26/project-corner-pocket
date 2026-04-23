package main;

public class AdminMenu {

    private User currentUser;      
    private MainMenu menu;

    private User targetUser;         
    private BankAccount targetAccount; 

    public AdminMenu(User currentUser, MainMenu menu) {
        this.currentUser = currentUser;
        this.menu = menu;
    }

    public void run() {

        int selection = -1;

        while (selection != 9) {

            displayMenu();
            selection = menu.getUserSelection(9);
            process(selection);
        }
    }



    private void displayMenu() {

        System.out.println("\nAdministrator: " + currentUser.getUsername());

        if (targetUser != null && targetAccount != null) {
            System.out.println("Selected User: " + targetUser.getUsername());
            System.out.println("Selected Account: " + targetAccount.getAccountName());
        } else {
            System.out.println("No user/account selected.");
        }

        System.out.println("1. Create account (for user)");
        System.out.println("2. Close account");
        System.out.println("3. Collect fees");
        System.out.println("4. Pay interest");
        System.out.println("5. Issue loan");
        System.out.println("6. Select user & account");
        System.out.println("7. Set password");
        System.out.println("8. Reset password");
        System.out.println("9. Logout");
    }



    private void process(int selection) {

        AdministratorAccount admin = getAdminAccount();

        switch (selection) {

            case 1 -> createAccount();

            case 2 -> closeAccount();

            case 3 -> collectFees(admin);

            case 4 -> payInterest(admin);

            case 5 -> issueLoan(admin);

            case 6 -> selectUserAndAccount();

            case 7 -> setPassword();

            case 8 -> resetPassword();
        }
    }



    private AdministratorAccount getAdminAccount() {

        for (BankAccount acc : currentUser.getAccounts().values()) {
            if (acc instanceof AdministratorAccount) {
                return (AdministratorAccount) acc;
            }
        }

        throw new IllegalStateException("No admin account found.");
    }



    private void selectUserAndAccount() {

        while (true) {

            System.out.println("\nAvailable users:");

            for (User user : menu.bank.getUsers().values()) {
                System.out.println("- " + user.getUsername());
            }

            System.out.print("Select user: ");
            String username = menu.keyboardInput.next();

            User selectedUser = menu.bank.getUser(username);

            if (selectedUser == null) {
                System.out.println("User not found. Try again.");
                continue;
            }

            if (selectedUser.getAccounts().isEmpty()) {
                System.out.println("User has no accounts.");
                continue;
            }

            targetUser = selectedUser;


            while (true) {

                System.out.println("\nAccounts for " + targetUser.getUsername() + ":");

                for (BankAccount acc : targetUser.getAccounts().values()) {
                    System.out.println("- " + acc.getAccountName());
                }

                System.out.print("Select account: ");
                String accName = menu.keyboardInput.next();

                BankAccount selectedAcc = targetUser.getAccounts().get(accName);

                if (selectedAcc != null) {
                    targetAccount = selectedAcc;
                    return;
                }

                System.out.println("Account not found. Try again.");
            }
        }
    }


    private void createAccount() {

        System.out.print("User for account: ");
        String username = menu.keyboardInput.next();

        User user = menu.bank.getUser(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Account name: ");
        String name = menu.keyboardInput.next();

        System.out.print("Account type: ");
        String type = menu.keyboardInput.next();

        try {
            user.createAccount(name, false, menu.bank, type);
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create account: " + e.getMessage());
        }
    }

    private void closeAccount() {

        if (targetUser == null || targetAccount == null) {
            System.out.println("No account selected.");
            return;
        }

        targetUser.removeAccount(targetAccount);
        targetAccount = null;
    }

    private void collectFees(AdministratorAccount admin) {

        if (!(targetAccount instanceof CustomerAccount acc)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Amount: ");
        double amount = menu.keyboardInput.nextDouble();

        admin.collectFees(acc, amount);
    }

    private void payInterest(AdministratorAccount admin) {

        if (!(targetAccount instanceof CustomerAccount acc)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Rate: ");
        int rate = menu.keyboardInput.nextInt();

        admin.payInterest(acc, rate);
    }

    private void issueLoan(AdministratorAccount admin) {

        if (!(targetAccount instanceof CustomerAccount acc)) {
            System.out.println("Invalid customer account.");
            return;
        }

        System.out.print("Amount: ");
        double amount = menu.keyboardInput.nextDouble();

        System.out.print("Interest rate: ");
        int rate = menu.keyboardInput.nextInt();

        admin.giveLoan(acc, amount, rate);
    }

    private void setPassword() {

        if (targetUser == null) {
            System.out.println("No user selected.");
            return;
        }

        System.out.print("New password: ");
        targetUser.setPassword(menu.keyboardInput.next());
    }

    private void resetPassword() {

        if (targetUser == null) {
            System.out.println("No user selected.");
            return;
        }

        targetUser.setPassword(null);
    }
}