package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    public List<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
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
}
