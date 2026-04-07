package main;

import java.util.HashMap;
import java.util.ArrayList;

public class Bank {

    private ArrayList<CustomerAccount> customerAccounts; 
    private ArrayList<AdministratorAccount> administratorAccounts;
    private HashMap<String, int[]> usernames;
    private final int ADMIN = 2;
    private final int CUSTOMER = 1;

    public Bank() {
        AdministratorAccount userAccount = new AdministratorAccount("admin","admin123");
        administratorAccounts.add(userAccount);
        CustomerAccount customer1 = new CustomerAccount("customer1");
        customerAccounts.add(customer1);

        usernames.put("admin", new int[]{ADMIN, 1});
        usernames.put("customer1", new int[]{CUSTOMER, 1});

    }
    
    public void createAccount(Boolean isAdmin, String accountName, String password) {
        if(isAdmin) {
            AdministratorAccount newAccount = new AdministratorAccount(accountName, password);
            administratorAccounts.add(newAccount);
            usernames.put(accountName, new int[]{ADMIN, administratorAccounts.size()});
        } else {
            CustomerAccount newAccount = new CustomerAccount(accountName);
            customerAccounts.add(newAccount);
            usernames.put(accountName, new int[]{CUSTOMER, customerAccounts.size()});
        }
    }

    public void closeAccount(String username) {
        int[] accountInfo = usernames.get(username);
        try{

        if (accountInfo[0] == ADMIN) {
            administratorAccounts.remove(accountInfo[1] - 1);
        }
        else{

            customerAccounts.remove(accountInfo[1] - 1);
            
        }
        usernames.remove(username);
        }
        catch (Exception e) {
            System.out.println("Account not found.");
        }
    }
    
    public ArrayList<CustomerAccount> getCustomerAccounts() {
        return customerAccounts;
    }
    public ArrayList<AdministratorAccount> getAdministratorAccounts() {
        return administratorAccounts;
    }

    public void performDeposit(String username, double amount) {
        int[] accountInfo = usernames.get(username);
        if(accountInfo[0] == CUSTOMER){
            customerAccounts.get(accountInfo[1] - 1).deposit(amount);
        } 
        else {
            System.out.println("Administrators cannot perform deposits."); 
        }
    }

    public void performWithdrawal(String username, double amount){
        int[] accountInfo = usernames.get(username);
        if(accountInfo[0] == CUSTOMER){
            customerAccounts.get(accountInfo[1] - 1).withdraw(amount);
        } else {
            System.out.println("Administrators cannot perform withdrawals."); 
        }
    }
    

    public void transferMoney(String fromUsername, String toUsername, double transferAmount) {
        int[] fromAccountInfo = usernames.get(fromUsername);
        int[] toAccountInfo = usernames.get(toUsername);
        if (fromAccountInfo[0] == ADMIN) {
            if(toAccountInfo[0] == CUSTOMER){
                administratorAccounts.get(fromAccountInfo[1] - 1).transferMoney(customerAccounts.get(toAccountInfo[1] - 1), transferAmount);
            }
            else{
                return; //cannot transfer from admin to admin - doesn't make sense to transfer from vault to vault.
            }
        }
        else{ 
            if(toAccountInfo[0] == ADMIN){
                customerAccounts.get(fromAccountInfo[1] - 1).transferMoney(administratorAccounts.get(toAccountInfo[1] - 1), transferAmount);
            }
            else{
                customerAccounts.get(fromAccountInfo[1] - 1).transferMoney(customerAccounts.get(toAccountInfo[1] - 1), transferAmount);
            }   
        } 
    }

    public double displayBalance(String username) {
        int[] accountInfo = usernames.get(username);
        if(accountInfo[0] == CUSTOMER){
            System.out.println("Your balance is: " + customerAccounts.get(accountInfo[1] - 1).getBalance());
            return customerAccounts.get(accountInfo[1] - 1).getBalance();
        } else {
            System.out.println("Administrators do not have balances."); 
            return 0;
        }
    }

    public void collectFees(String username,double amount) {
        int[] accountInfo = usernames.get(username);
        if(accountInfo[0] == ADMIN){
            customerAccounts.get(accountInfo[1] - 1).withdraw(amount);
        } else {
            System.out.println("Only administrators can collect fees from a customer's account."); 
        }
    }

    public void payInterest(String username, double interestRate) {
        int[] accountInfo = usernames.get(username);
        if(accountInfo[0] == ADMIN){
            customerAccounts.get(accountInfo[1] - 1).deposit(customerAccounts.get(accountInfo[1] - 1).getBalance() * interestRate);
        } else {
            System.out.println("Only administrators can pay interest to a customer's account.");; 
        }
    }

    public void setPassword(int accountNumber, String Password) {
        accounts.get(accountNumber - 1).setPassword(Password);
    }

    public String getPassword(int accountNumber) {
        return accounts.get(accountNumber - 1).getPassword();
    }

    public void performPasswordReset(int accountNumber) {
        accounts.get(accountNumber - 1).resetPassword();
    }
}
