package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


public class MainMenu {

    private static final int EXIT_SELECTION = 12;
	private static final int MAX_SELECTION = 12;

    private Scanner keyboardInput;
    private Bank bank;
    private String currentAccount;

    public MainMenu() {
        //this.userAccount = new BankAccount();
        this.bank = new Bank();

        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {

        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. Make a deposit");
        System.out.println("2. Create a new account");
        System.out.println("3. Close Account");
        System.out.println("4. Make a withdrawal");
        System.out.println("5. Check Balance");
        System.out.println("6. Check transaction history");
        System.out.println("7. Transfer money");
        System.out.println("8. Collect fees");
        System.out.println("9. Pay interest");
        System.out.println("10. Set password");
        System.out.println("11. Reset password");
        System.out.println("12. Switch Account");
        System.out.println("13. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        int accountNumber;
        switch (selection) {
            case 1:
                if (checkPassword()) {
                    performDeposit();
                }
                break;
            case 2:
                createAccount();
                break;
            case 3:
                if (checkPassword()) {
                    closeAccount();
                }
                break;
            case 4:
                if (checkPassword()) {
                    peformWithdraw();
                }
                break;
            case 5:
                if (checkPassword()) {
                    displayBalance();
                }
                break;
            case 6:
                if (checkPassword()) {
                    displayTransactionHistory();
                }
                break;
            case 7:
                if (!checkPassword()) {
                    break;
                }
                System.out.print("To which account: ");
                String toAccount = keyboardInput.next();
                String tempAccount = currentAccount;
                currentAccount = toAccount;
                if (!checkPassword()) {
                    break;
                }
                currentAccount = tempAccount;
                transferMoney(toAccount);

                break;
            case 8:
                 System.out.print("How much would you like to collect in fees: ");
                 double feeAmount = keyboardInput.nextDouble();
                 System.out.print("From which account: ");
                 String customerAccount = keyboardInput.next();
                 if (!checkPassword()) {
                     break;
                 }
                 bank.collectFees(currentAccount, customerAccount, feeAmount);
                 break;
            case 9:
                System.out.print("What interest would you like to pay to the customer: ");
                double interestRate = keyboardInput.nextDouble();
                System.out.print("To which account: ");
                customerAccount = keyboardInput.next();
                if (!checkPassword()) {
                    break;
                }
                payInterest(customerAccount, interestRate);
                break;
            case 10:
                if (checkPassword()) {
                    setPassword();
                }
                break;
            case 11:
                if (checkPassword()) {
                    resetPassword();
                }
                break;
            case 12:
                this.currentAccount = switchAccount();
                break;

            case 13:
                System.exit(0);
        }
    }

    public void createAccount() {
        System.out.print("Is this an administrator account? (true/false): ");
        Boolean isAdmin = keyboardInput.nextBoolean();
        System.out.print("What is the account's name?: ");
        String username = keyboardInput.next();
        String password = "";

        if(isAdmin){
            System.out.print("What is the password for the administrator account?: ");
            password = keyboardInput.next();

        }

        bank.createAccount(isAdmin, username, password);
    }

    public void closeAccount() {
        bank.closeAccount(currentAccount);
        switchAccount();                     //user has to change accounts if they were to remove one
    }


    /*public ArrayList<BankAccount> getAccounts() {
        return bank.getAccounts();
    }

    public int getNumberOfAccounts() {
        return bank.getNumberOfAccounts();
    }*/

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        bank.performDeposit(currentAccount, depositAmount);
    }

    public double displayBalance() {
        return bank.displayBalance(currentAccount);
    }


    public void displayTransactionHistory() {
        for (String line : bank.getAccounts().get(accountNumber-1).getTransactionHistory()) {
            System.out.println(line);
        }
    }
    

    public void peformWithdraw() {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        bank.performWithdrawal(currentAccount, withdrawAmount);
    }
    
    public void transferMoney(String toAccount) {
        double transferAmount = -1;
        while (transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextInt();
        }
        bank.transferMoney(currentAccount, toAccount, transferAmount);
    }

    public void collectFees(String customerAccount, double amount) {
        bank.collectFees(currentAccount, customerAccount, amount);
    }

    public void payInterest(String customerAccount, double interestRate) {
        bank.payInterest(currentAccount, customerAccount, interestRate);
    }

    /// Password Managment
    public boolean checkPassword() {
        if (getPassword() != null){
            System.out.print("Please enter the password: ");
            String password = keyboardInput.next();
            if (!password.equals(getPassword())) {
                System.out.println("Incorrect password, returning to main menu.");
                return false;
            }
            return true;
        }
        return true;
    } 
    
    
    public String getPassword() {
        return bank.getPassword(currentAccount);
    }

    public void setPassword() {
        System.out.print("Please enter a new password: ");
        String password = keyboardInput.next();
        bank.setPassword(currentAccount, password);
    }

    public void resetPassword() {
        bank.performPasswordReset(currentAccount);
    }

    public String switchAccount() {

        HashMap<String, BankAccount> accounts = bank.getAccounts();

        for (String username : accounts.keySet()) {
            System.out.println("user: " + accounts.get(username));
        }

        System.out.print("Please select an account:");
        String selection = keyboardInput.next();
        return selection;
    }

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
