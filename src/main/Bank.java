package main;

import java.util.HashMap;

public class Bank {

    private HashMap<String, User> users = new HashMap<>();
    private double bankVaultBalance;

    public Bank(double bankVaultBalance) {
        this.bankVaultBalance = bankVaultBalance;

        User root = new User("root", "toor", true);
        root.addAccount(new AdministratorAccount("root", this));

        users.put("root", root);
    }

    public void createUser(String username, String password, Boolean isAdmin) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User exists");
        }
        users.put(username, new User(username, password, isAdmin));
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean canWithdraw(BankAccount account) {
        if (account instanceof AdministratorAccount) return false;
        return !"Educational Account".equals(account.getAccountType());
    }

    public boolean canTransfer(BankAccount from, BankAccount to) {

        String fromType = from.getAccountType();
        String toType = to.getAccountType();

        if ("Investment Account".equals(fromType)) return false;

        return !("Educational Account".equals(fromType)
              || "Educational Account".equals(toType));
    }

    public void transfer(BankAccount from, BankAccount to, double amount) {

        if (amount <= 0) throw new IllegalArgumentException();

        if (!canTransfer(from, to)) {
            throw new IllegalArgumentException("Transfer not allowed");
        }

        if (from instanceof CustomerAccount) {
            ((CustomerAccount) from).withdraw(amount);
        }

        if (to instanceof CustomerAccount) {
            ((CustomerAccount) to).deposit(amount);
        }
    }

    public void depositToVault(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        bankVaultBalance += amount;
    }

    public void withdrawFromVault(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        if (bankVaultBalance < amount) {
            throw new IllegalArgumentException("Insufficient vault funds");
        }
        bankVaultBalance -= amount;
    }

    public void transferToVault(CustomerAccount customer, double amount){
        if (amount <= 0) throw new IllegalArgumentException();

        customer.withdraw(amount);
        depositToVault(amount);
    }

    public double getBankVaultBalance() {
        return bankVaultBalance;
    }
}