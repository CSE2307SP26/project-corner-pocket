package main;

import java.util.ArrayList;
import java.util.Scanner;


public class MainMenu {

    private static final int EXIT_SELECTION = 12;
	private static final int MAX_SELECTION = 12;

    private Scanner keyboardInput;
    private Bank bank;
    private BankAccount currentAccount;

    public MainMenu() {
        //this.userAccount = new BankAccount();
        this.bank = new Bank();
        this.currentAccount = bank.getAccounts().get(1);

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
        System.out.println("12. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }
    
    public int getNumber() {
        for (int i = 0; i < bank.getNumberOfAccounts(); i++) {
            System.out.println("Account " + (i + 1));
        }
        System.out.print("Please select an account:");
        int selection = keyboardInput.nextInt();
        return selection;
    }

    public void processInput(int selection) {
        int accountNumber;
        switch (selection) {
            case 1:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    performDeposit(currentAccount,accountNumber);
                }
                break;
            case 2:
                createAccount();
                break;
            case 3:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    closeAccount(accountNumber);
                }
                break;
            case 4:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    peformWithdraw(accountNumber);
                }
                break;
            case 5:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    displayBalance(accountNumber);
                }
                break;
            case 6:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    displayTransactionHistory(accountNumber);
                }
                break;
            case 7:
                System.out.print("From which account: ");
                int fromAccount = keyboardInput.nextInt();
                if (!checkPassword(fromAccount)) {
                    break;
                }
                System.out.print("To which account: ");
                int toAccount = keyboardInput.nextInt();
                if (!checkPassword(toAccount)) {
                    break;
                }
                transferMoney(fromAccount, toAccount);
                break;
            case 8:
                 System.out.print("How much would you like to collect in fees: ");
                 double feeAmount = keyboardInput.nextDouble();
                 System.out.print("From which account: ");
                 accountNumber = keyboardInput.nextInt();
                 if (!checkPassword(accountNumber)) {
                     break;
                 }
                 collectFees(accountNumber, feeAmount);
                 break;
            case 9:
                System.out.print("What interest rate would you like to pay: ");
                double interestRate = keyboardInput.nextDouble();
                System.out.print("To which account: ");
                accountNumber = keyboardInput.nextInt();
                if (!checkPassword(accountNumber)) {
                    break;
                }
                payInterest(accountNumber, interestRate);
                break;
            case 10:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    setPassword(accountNumber);
                }
                break;
            case 11:
                accountNumber = getNumber();
                if (checkPassword(accountNumber)) {
                    resetPassword(accountNumber);
                }
                break;
            case 12:
                System.exit(0);
        }
    }

    public void createAccount() {
        bank.createAccount();
    }

    public void closeAccount(int accountIndex) {
        bank.closeAccount(accountIndex);
    }


    public ArrayList<BankAccount> getAccounts() {
        return bank.getAccounts();
    }

    public int getNumberOfAccounts() {
        return bank.getNumberOfAccounts();
    }

    public void performDeposit(int accountNumber) {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        bank.performDeposit(accountNumber, depositAmount);
    }

    public double displayBalance(int accountNumber) {
        return bank.displayBalance(accountNumber);
    }


    public void displayTransactionHistory(int accountNumber) {
        for (String line : bank.getAccounts().get(accountNumber-1).getTransactionHistory()) {
            System.out.println(line);
        }
    }
    

    public void peformWithdraw(int accountNumber) {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        bank.performWithdrawal(accountNumber, withdrawAmount);
    }
    
    public void transferMoney(int fromAccount, int toAccount) {
        double transferAmount = -1;
        while (transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextInt();
        }
        bank.transferMoney(fromAccount, toAccount, transferAmount);
    }

    public void collectFees(int accountNumber, double amount) {
        bank.collectFees(accountNumber, amount);
    }

    public void payInterest(int accountNumber, double interestRate) {
        bank.payInterest(accountNumber, interestRate);
    }

    /// Password Managment
    public boolean checkPassword(int accountNumber) {
        if (getPassword(accountNumber) != null){
            System.out.print("Please enter the password: ");
            String password = keyboardInput.next();
            if (!password.equals(getPassword(accountNumber))) {
                System.out.println("Incorrect password, returning to main menu.");
                return false;
            }
            return true;
        }
        return true;
    } 
    
    
    public String getPassword(int accountNumber) {
        return bank.getPassword(accountNumber);
    }

    public void setPassword(int accountNumber) {
        System.out.print("Please enter a new password: ");
        String password = keyboardInput.next();
        bank.setPassword(accountNumber, password);
    }

    public void resetPassword(int accountNumber) {
        bank.performPasswordReset(accountNumber);
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
