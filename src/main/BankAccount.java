package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && this.balance >= amount){
            this.balance -= amount;
        } else if (amount > 0 && this.balance < amount){
            this.balance = 0;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public double getBalance() {
        return this.balance;
    }
}
