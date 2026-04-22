package main;
import java.util.ArrayList;

public class CustomerAccount extends BankAccount {

    private double balance;
    private double loanAmount;
    private ArrayList<String> transactionHistory;

    public CustomerAccount(String accountName) {
        super(accountName);
        this.balance = 0;
        this.loanAmount = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        if (balance < amount) throw new IllegalArgumentException("Insufficient funds");

        balance -= amount;
        transactionHistory.add("Withdrew: $" + amount);
    }

    public double getBalance() {
        return balance;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void payLoan(double amount){

    }

    public ArrayList<String> getTransactionHistory() {
      return new ArrayList<>(transactionHistory);
    }
}