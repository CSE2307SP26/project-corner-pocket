package main;

import java.util.HashMap;
import java.util.Scanner;


public class MainMenu {

    private static int EXIT_SELECTION = 11;
	private static int MAX_SELECTION = 11;

    private Scanner keyboardInput;
    private Bank bank;
    private String currentAccount = null;

    public MainMenu() {
        this.bank = new Bank(10000.00);
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        if(currentAccount == null){
            EXIT_SELECTION = 3;
            MAX_SELECTION = 3;

            System.out.println("Currently not logged in, you have the following options: ");
            System.out.println("1. Create a new account");
            System.out.println("2. Switch to an existing Account");
            System.out.println("3. Exit the app");




        }
        
        else if(bank.getAccounts().get(currentAccount) instanceof CustomerAccount){

          EXIT_SELECTION = 12;
          MAX_SELECTION = 12;
            
          System.out.println("Current Customer: " + currentAccount);
          System.out.println("1. Make a deposit");
          System.out.println("2. Create a new account");
          System.out.println("3. Close this account");
          System.out.println("4. Make a withdrawal");
          System.out.println("5. Check Balance");
          System.out.println("6. Check transaction history");
          System.out.println("7. Transfer money to another account");
          System.out.println("8. Pay back part of loan");
          System.out.println("9. Set password");
          System.out.println("10. Reset password");
          System.out.println("11. Switch to an existing account");
          System.out.println("12. Exit the app");
        }

        else{
            EXIT_SELECTION = 9;
            MAX_SELECTION = 9;
            System.out.println("Current Administrator: " + currentAccount);
            System.out.println("1. Create a new account");
            System.out.println("2. Close this account");
            System.out.println("3. Collect fees");
            System.out.println("4. Pay interest to a customer");
            System.out.println("5. Issue loan to a customer");
            System.out.println("6. Set password");
            System.out.println("7. Reset password");
            System.out.println("8. Switch to an existing account");
            System.out.println("9. Exit the app");
        }

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

       if(currentAccount == null){
        switch (selection){
            case 1: 
                createAccount();
                break;
            
            case 2: 
                switchAccount();

            case 3:
                System.exit(0); 

        }
       } 

       else if(bank.getAccounts().get(currentAccount) instanceof CustomerAccount){
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
                if (!checkPassword()){
                    break;
                }
                performPayLoan();


            case 9:
                
                if (checkPassword()) {
                    setPassword();
                }
                break;
            case 10:
                if (checkPassword()) {
                    resetPassword();
                }
                break;
            case 11:
                switchAccount();
                break;

            case 12:
                System.exit(0);
            }
        }

           
        else{
            switch(selection){
            case 1:
                createAccount();
                break;

            case 2:
                if (checkPassword()) {
                    closeAccount();
                }
                break;

            case 3:
                 System.out.print("How much would you like to collect in fees: ");
                 double feeAmount = keyboardInput.nextDouble();
                 System.out.print("From which account: ");
                 String customerAccount = keyboardInput.next();
                 if (!checkPassword()) {
                     break;
                 }
                 bank.collectFees(currentAccount, customerAccount, feeAmount);
                 break;

            case 4:
                System.out.print("What interest would you like to pay to the customer (percentage): ");
                int interestRate = keyboardInput.nextInt();
                System.out.print("To which account?: ");
                customerAccount = keyboardInput.next();
                if (!checkPassword()) {
                    break;
                }
                payInterest(customerAccount, interestRate);
                break;

            case 5:
                if (checkPassword()) {
                    setPassword();
                }
                break;

            case 6:
                if (checkPassword()) {
                    resetPassword();
                }
                break;

            case 7:
                switchAccount();
                break;

            case 8:
                System.exit(0);
            }
        }
    }

    public void createAccount() {
        System.out.print("Is this an administrator account? (true/false) ");
        Boolean isAdmin = keyboardInput.nextBoolean();

        /// Asks what account type you want to creat 
        String accountType = "Standard Account";
        if(!isAdmin){
            System.out.print("What kind of account do you want to create?\n1. Standard Account\n2. Educational Account\n3. Investment Account\n:");
            int selection = keyboardInput.nextInt();
            while(selection < 1 || selection > 3){
                System.out.print("Invalid selection, please select 1, 2, or 3: ");
                selection = keyboardInput.nextInt();
            }
             switch(selection){
                case 1:
                    accountType = "Standard Account";
                    break;
                case 2:
                    accountType = "Educational Account";
                    break;
                case 3:
                    accountType = "Investment Account";
                    break;
            }
        }

        System.out.print("What is the account's name?: ");
        String username = keyboardInput.next();
        String password = "";
        Boolean wantsPassword;

        if(isAdmin){
            System.out.print("A password is required for the administrator account, what do you wish for it to be? ");
            password = keyboardInput.next();

        }
        else{
            System.out.print("Do you wish to have a password? (true/false) ");
            wantsPassword = keyboardInput.nextBoolean();
            if(wantsPassword){
                System.out.print("What do you wish for your password to be? ");
                password = keyboardInput.next();
            }
        }
        try{
            bank.createAccount(isAdmin, username, password);
        }
        catch(IllegalArgumentException e){
            System.out.println("Username is already taken!");
        }

        // Sets the account type
        bank.setAccountType(username, accountType);
    }

    public void closeAccount() {
        try{
            bank.closeAccount(currentAccount);
            currentAccount = null;
        }
        catch(IllegalArgumentException e){
            System.out.println("Username already doesn't exist");
        }
    }


    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        try{
            bank.performDeposit(currentAccount, depositAmount);
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid deposit amount!");
        }
    }

    public void displayBalance() {
        System.out.println(bank.displayBalance(currentAccount));
    }


    public void displayTransactionHistory() {
        CustomerAccount customerAccount = (CustomerAccount) bank.getAccounts().get(currentAccount);
        for (String line : customerAccount.getTransactionHistory()) {
             System.out.println(line);
        }
        
    }
    

    public void peformWithdraw() {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        try{
            bank.performWithdrawal(currentAccount, withdrawAmount);
        }
        catch(IllegalArgumentException e){

            System.out.println("Invalid Withdrawl Amount!");

        }
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
        AdministratorAccount currentAdminAccount = (AdministratorAccount) bank.getAccounts().get(currentAccount);
        currentAdminAccount.updateLocalBankVault(bank.getBankVaultBalance());
        try{
            bank.collectFees(currentAccount, customerAccount, amount);
        }
        catch(IllegalArgumentException e){
            System.out.println("Customer has insufficient funds");
        }
    }

    public void payInterest(String customerAccount, int interestRate) {
        AdministratorAccount currentAdminAccount = (AdministratorAccount) bank.getAccounts().get(currentAccount);
        currentAdminAccount.updateLocalBankVault(bank.getBankVaultBalance());
        int interest = interestRate;
        // If the account is an investment account, it receives double the interest rate
        if (bank.getAccountType(currentAccount).equals("Investment Account")) {
            interest = interestRate * 2;
        }
        try{
         bank.payInterest(currentAccount, customerAccount, interestRate);
        }
        catch(IllegalArgumentException e){
            System.out.println("Bank does not have enough money to give to customer!");
        }
    }

    public void performPayLoan(){
        System.out.print("Which administrator are you wishing to process your payment?");
        String adminAccount = keyboardInput.next();
        System.out.print("How much are you wishin to pay?");
        double amount = keyboardInput.nextDouble();
        if(bank.getAccounts().containsKey(adminAccount)){
            try{
                bank.performPayLoan(currentAccount, adminAccount, amount);
            }
            catch(IllegalArgumentException e){
                return;
            }
        }
        else{
            System.out.println("Admin account does not exist!");
        }


    }

    public void performGiveLoan(){


        AdministratorAccount currentAdminAccount = (AdministratorAccount) bank.getAccounts().get(currentAccount);
        currentAdminAccount.updateLocalBankVault(bank.getBankVaultBalance());

        System.out.print("Which customer are you wishing to issue a loan?");
        String customerAccount = keyboardInput.next();
        if(bank.getAccounts().containsKey(customerAccount)){
            System.out.print("How much is the loan?");
            double amount = keyboardInput.nextDouble();
            System.out.print("With what interest rate?");
            int interestRate = keyboardInput.nextInt();
            try{
                bank.performGiveLoan(currentAccount, customerAccount, amount, interestRate);
            }
            catch(IllegalArgumentException e){
                System.out.println("That is not a customer account!");
            }
        }
        
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

    public void switchAccount() {

        HashMap<String, BankAccount> accounts = bank.getAccounts();
        
        if(accounts.size() > 0){
        for (String username : accounts.keySet()) {
            // Get the account object so we can use it
            BankAccount acc = accounts.get(username); 
            
            // Print the username (key) followed by the account details
            System.out.println("- " + username + " [" + acc.getAccountType() + "]");
        }

        System.out.print("Please select an account:");
        String username = keyboardInput.next();
        if(accounts.containsKey(username)){
            this.currentAccount = username;
        }
        else{
            System.out.println("Account does not exist!");
        }
      }
      else{

        System.out.println("There are currently no accounts, please create one");

      }
    
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
