package main;

import java.util.ArrayList;

public class CustomerAccount extends BankAccount {

    private double balance;
    private double loanAmount;
    public ArrayList<String> transactionHistory;

    public CustomerAccount(String accountName) {
        super(accountName, null);
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.loanAmount = 0;
        
    }

     public void deposit(double amount) {    
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: $"+amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && this.balance >= amount){
            this.balance -= amount;
            this.transactionHistory.add("Withdrew: $"+amount);
        } else if (amount > 0 && this.balance < amount){
            this.transactionHistory.add("Withdrew: $"+this.balance);
            this.balance = 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public ArrayList<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void transferMoney(BankAccount toAccount, double transferAmount) {
        this.withdraw(transferAmount);
        toAccount.receiveTransfer(transferAmount);
    }

    public void receiveTransfer(double transferAmount) {
        this.deposit(transferAmount);
    }

    public void payLoan(BankAccount toAccount, double amount) {
        if (toAccount instanceof AdministratorAccount) {
            if (loanAmount < amount) {

                this.transferMoney(toAccount, amount);
            }
            else {
                System.out.println("Cannot transfer that much");
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    
}
