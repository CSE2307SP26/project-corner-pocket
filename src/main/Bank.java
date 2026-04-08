package main;

import java.util.HashMap;


public class Bank {

    private HashMap<String, BankAccount> accounts = new HashMap<String, BankAccount>();
    private double bankVaultBalance;
    

    public Bank(double bankVaultBalance) {

        accounts.put("admin1", new AdministratorAccount("admin1", "admin123", this));
        accounts.put("customer1" , new CustomerAccount("customer1"));
        this.bankVaultBalance = bankVaultBalance;
    
    }

    public double getBankVaultBalance(){

        return this.bankVaultBalance;

    }
    
    public void setBankVaultBalance(double amount){

        this.bankVaultBalance = amount;

    }
    
    public void createAccount(Boolean isAdmin, String accountName, String password) {
        if(isAdmin) {

            accounts.put(accountName, new AdministratorAccount(accountName, password, this));
            
        } else {
           accounts.put(accountName, new CustomerAccount(accountName));
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
        if(account instanceof CustomerAccount){
            CustomerAccount customerAccount = (CustomerAccount) accounts.get(username);
            customerAccount.deposit(amount);
        } else {
            System.out.println("Administrators cannot perform withdrawals."); 
        }
    }
    

    public void transferMoney(String fromUsername, String toUsername, double transferAmount) {

       accounts.get(fromUsername).transferMoney(accounts.get(toUsername), transferAmount);

    }

    public double displayBalance(String username) {
          BankAccount account = accounts.get(username);
        if(account instanceof CustomerAccount){
            CustomerAccount customerAccount = (CustomerAccount) accounts.get(username);
            return customerAccount.getBalance();
        } else {
            System.out.println("Administrators do not have balances."); 
            return 0;
        }
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

    public void payInterest(String adminUsername, String customerUsername, double interestRate) {
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

    public void setPassword(String username, String Password) {
        accounts.get(username).setPassword(Password);
    }

    public String getPassword(String username) {
        return accounts.get(username).getPassword();
    }

    public void performPasswordReset(String username) {
        accounts.get(username).resetPassword();
    }
}
