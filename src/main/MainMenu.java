package main;

import java.util.Scanner;
import java.util.ArrayList;


public class MainMenu {

    private static final int EXIT_SELECTION = 7;
	private static final int MAX_SELECTION = 6;

    private Scanner keyboardInput;
    private ArrayList<BankAccount> accounts;

    public MainMenu() {
        //this.userAccount = new BankAccount();
        this.accounts = new ArrayList<BankAccount>();
        BankAccount userAccount = new BankAccount();
        accounts.add(userAccount);

        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Create a new account");
        System.out.println("3. Withdraw from account");
        System.out.println("4. Check Balance");
        System.out.println("5. Check transaction history");
        System.out.println("6. Transfer money");
        System.out.println("7. Exit the app");

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
                peformWithdraw(accountNumber);
                break;
            case 4:
                accountNumber = getNumber();
                displayBalance(accountNumber);
                break;
            case 5:
                accountNumber = getNumber();
                displayTransactionHistory(accountNumber);
                break;
            case 6:
                System.out.print("From which account: ");
                int fromAccount = keyboardInput.nextInt();
                System.out.print("To which account: ");
                int toAccount = keyboardInput.nextInt();
                transferMoney(fromAccount, toAccount);
                break;
            case 7:
                System.exit(0);
        }
    }

    public void createAccount() {
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
    }

    public void closeAccount(int accountIndex) {
           
        if(accountIndex >= 0 && accountIndex < accounts.size()) {

            accounts.remove(accountIndex);

        } else {

            throw new IllegalArgumentException();

        }
    }


    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void performDeposit(int accountNumber) {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        accounts.get(accountNumber-1).deposit(depositAmount);
    }

    public void displayBalance(int accountNumber) {
        System.out.println(accounts.get(accountNumber-1).getBalance());
    }


    public void displayTransactionHistory(int accountNumber) {
        for (String line : accounts.get(accountNumber - 1).transactionHistory) {
            System.out.println(line);
        }
    }
    

    public void peformWithdraw(int accountNumber) {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        accounts.get(accountNumber - 1).withdraw(withdrawAmount);
    }
    
    public void transferMoney(int fromAccount, int toAccount) {
        double transferAmount = -1;
        while (transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextInt();
        }
        accounts.get(fromAccount - 1).withdraw(transferAmount);
        accounts.get(toAccount - 1).deposit(transferAmount);
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
