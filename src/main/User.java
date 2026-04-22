package main;

import java.util.ArrayList;

public class User {

    private final String username;
    private String password;
    private final ArrayList<BankAccount> accounts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(BankAccount account) {
    for (BankAccount acc : accounts) {
        if (acc.getAccountName().equals(account.getAccountName())) {
            throw new IllegalArgumentException("Account name already exists");
        }
    }
    accounts.add(account);
}

    public void removeAccount(BankAccount account) {
        accounts.remove(account);
    }

    public boolean isAdmin() {
    for (BankAccount account : accounts) {
        if (account instanceof AdministratorAccount) {
            return true;
        }
    }
    return false;
}
}