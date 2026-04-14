package main;

import java.util.HashMap;


public class Bank {

    private HashMap<String, BankAccount> accounts = new HashMap<String, BankAccount>();
    private double bankVaultBalance;
    

    public Bank(double bankVaultBalance) {

       
        this.bankVaultBalance = bankVaultBalance;
        accounts.put("root", new AdministratorAccount("root", "toor", this.bankVaultBalance));
    
    }

    public double getBankVaultBalance(){

        return this.bankVaultBalance;

    }
    
    public void setBankVaultBalance(double amount){

        this.bankVaultBalance = amount;

    }
    
    public void createAccount(Boolean isAdmin, String accountName, String password) {
        if(!accounts.containsKey(accountName)){
            if(isAdmin) {

                accounts.put(accountName, new AdministratorAccount(accountName, password, this.bankVaultBalance));
            
            } else {
             accounts.put(accountName, new CustomerAccount(accountName));
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public void closeAccount(String username) {
      
        if(accounts.remove(username) == null){
            throw new IllegalArgumentException();
        }

    }
    
    public HashMap<String, BankAccount> getAccounts() {
        return accounts;
    }
    

    public void performDeposit(String username, double amount) {
        BankAccount account = accounts.get(username);
        if(account instanceof CustomerAccount){
           CustomerAccount customerAccount = (CustomerAccount) accounts.get(username);
           customerAccount.deposit(amount);
        } 
        else {
            System.out.println("Administrators cannot perform deposits."); 
        }
    }

    public void performWithdrawal(String username, double amount){
        BankAccount account = accounts.get(username);
        if(account instanceof CustomerAccount && getAccountType(username).equals("Standard Account")){
            CustomerAccount customerAccount = (CustomerAccount) accounts.get(username);
            customerAccount.withdraw(amount);
        } else if (getAccountType(username).equals("Educational Account")){
            System.out.println("Educational accounts cannot perform withdrawals.");
        } else {
            System.out.println("Administrators cannot perform withdrawals."); 
        }
    }
    

    public void transferMoney(String fromUsername, String toUsername, double transferAmount) {
        // Checks to see if either account is not an educational account.
        if (!accounts.get(fromUsername).getAccountType().equals("Educational Account") ||
            !accounts.get(toUsername).getAccountType().equals("Educational Account")) {
            System.out.println("Educational accounts cannot perform transfers between non Educational Accounts!");
            return;
        }
        accounts.get(fromUsername).transferMoney(accounts.get(toUsername), transferAmount);

    }

    public double displayBalance(String username) {

        CustomerAccount customerAccount = (CustomerAccount) accounts.get(username);
        return customerAccount.getBalance();
       
    }

    public void collectFees(String adminUsername, String customerUsername, double amount) {
        BankAccount account = accounts.get(adminUsername);
        BankAccount customerAccount = accounts.get(customerUsername);

        if(account instanceof AdministratorAccount){
            AdministratorAccount administratorAccount = (AdministratorAccount) account;

            administratorAccount.collectFees(customerAccount, amount);
        
        } else {
            System.out.println("Only administrators can collect fees from a customer's account."); 
        }
    }

    public void payInterest(String adminUsername, String customerUsername, int interestRate) {
        BankAccount account = accounts.get(adminUsername);
        BankAccount toAccount = accounts.get(customerUsername);
        
        if(account instanceof AdministratorAccount){
           AdministratorAccount administratorAccount = (AdministratorAccount) account;
           if(toAccount instanceof CustomerAccount){
             CustomerAccount customerAccount = (CustomerAccount) toAccount;
             administratorAccount.payInterest(customerAccount, interestRate);
           }
           else{
            System.out.print("Interest rates can only be paid to customer accounts");
           }
        } else {
            System.out.println("Only administrators can pay interest to a customer's account"); 
        }
    }

    public void performPayLoan(String customerUsername, String adminUsername, double amount){
        CustomerAccount customerAccount = (CustomerAccount) accounts.get(customerUsername);
        BankAccount administratorAccount = accounts.get(adminUsername);
        if(administratorAccount instanceof AdministratorAccount){
           AdministratorAccount adminAccount = (AdministratorAccount) administratorAccount;
           adminAccount.updateLocalBankVault(this.bankVaultBalance);
           customerAccount.payLoan(accounts.get(adminUsername), amount);
        }
        else{
            System.out.println("This is not an admin account!");
        }
    }

    public void performGiveLoan(String adminUsername, String customerUsername, double amount, double interest){

    }

    public String getPassword(String username) {
        return accounts.get(username).getPassword();
    }

    public void setPassword(String username, String password) {
        accounts.get(username).setPassword(password);

    }

    public void performPasswordReset(String username) {
        accounts.get(username).resetPassword();
    }


    // sets Account type for a given account
    public void setAccountType(String username, String accountType) {
        accounts.get(username).setAccountType(accountType);
    }


    //Gets the account type for a given account
    public String getAccountType(String username) {
        return accounts.get(username).getAccountType();
    }

    
}
