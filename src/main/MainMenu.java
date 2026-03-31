package main;

import java.util.Scanner;
import java.util.ArrayList;


public class MainMenu {

    private static final int EXIT_SELECTION = 10;
	private static final int MAX_SELECTION = 10;

    private Scanner keyboardInput;
    private Bank bank;

    public MainMenu() {
        //this.userAccount = new BankAccount();
        this.bank = new Bank();

        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Create a new account");
        System.out.println("3. Close an account");
        System.out.println("4. Withdraw from account");
        System.out.println("5. Check Balance");
        System.out.println("6. Check transaction history");
        System.out.println("7. Transfer money");
        System.out.println("8. Collect fees");
        System.out.println("9. Pay interest");
        System.out.println("10. Exit the app");

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
        System.out.print("Please select an account:");
        int selection = keyboardInput.nextInt();
        return selection;
    }

    public void processInput(int selection) {
        int accountNumber;
        switch (selection) {
            case 1:
                accountNumber = getNumber();
                performDeposit(accountNumber);
                break;
            case 2:
                createAccount();
                break;
            case 3:
                accountNumber = getNumber();
                closeAccount(accountNumber);
                break;
            case 4:
                accountNumber = getNumber();
                peformWithdraw(accountNumber);
                break;
            case 5:
                accountNumber = getNumber();
                displayBalance(accountNumber);
                break;
            case 6:
                accountNumber = getNumber();
                displayTransactionHistory(accountNumber);
                break;
            case 7:
                System.out.print("From which account: ");
                int fromAccount = keyboardInput.nextInt();
                System.out.print("To which account: ");
                int toAccount = keyboardInput.nextInt();
                transferMoney(fromAccount, toAccount);
                break;
            case 8:
                 System.out.print("How much would you like to collect in fees: ");
                 double feeAmount = keyboardInput.nextDouble();
                 System.out.print("From which account: ");
                 accountNumber = keyboardInput.nextInt();
                 collectFees(accountNumber, feeAmount);
                 break;
            case 9:
                System.out.print("What interest rate would you like to pay: ");
                double interestRate = keyboardInput.nextDouble();
                System.out.print("To which account: ");
                accountNumber = keyboardInput.nextInt();
                payInterest(accountNumber, interestRate);
                break;
            case 10:
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
        for (String line : bank.getAccounts().get(accountNumber-1).transactionHistory) {
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
