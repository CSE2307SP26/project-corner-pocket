package main;

import java.util.ArrayList;

public class Bank {

    private ArrayList<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<BankAccount>();
        BankAccount userAccount = new BankAccount();
        accounts.add(userAccount);

    }
    
    public void createAccount() {
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
    }

    public void closeAccount(int accountIndex) {     
        if(accountIndex > 0 && accountIndex <= getNumberOfAccounts()) {
            accounts.remove(accountIndex);
        } 
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void performDeposit(int accountNumber, double amount) {
        accounts.get(accountNumber - 1).deposit(amount);
    }

    public void performWithdrawal(int accountNumber, double amount){
        accounts.get(accountNumber - 1).withdraw(amount);
    }

    public void transferMoney(int fromAccount, int toAccount, double transferAmount) {
        accounts.get(fromAccount - 1).withdraw(transferAmount);
        accounts.get(toAccount - 1).deposit(transferAmount);
    }

    public double displayBalance(int accountNumber) {
        return accounts.get(accountNumber - 1).getBalance();
    }

}
