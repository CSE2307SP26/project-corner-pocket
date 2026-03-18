package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 5;
	private static final int MAX_SELECTION = 5;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Withdraw from account");
        System.out.println("3. Check Balance");
        System.out.println("4. Check transaction history");
        System.out.println("5. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                peformWithdraw();
                break;
            case 3:
                displayBalance();
                break;
            case 4:
                displayTransactionHistory();
                break;
            case 5:
                System.exit(0);
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
    }

    public void displayBalance() {
        System.out.println(userAccount.getBalance());
    }


    public void displayTransactionHistory() {
        for(String line : userAccount.transactionHistory){
            System.out.println(line);
        }
    }
    

    public void peformWithdraw() {
        double withdrawAmount = -1;
        while(withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        userAccount.withdraw(withdrawAmount);
    }



    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
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
