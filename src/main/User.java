package main;

import java.util.HashMap;

public class User {

    private final String username;
    private String password;
    private final HashMap<String, BankAccount> accounts;
    private boolean isAdmin;
    private int age;

    public User(String username, String password, Boolean isAdmin, int age) {
        this.username = username;
        this.password = password;
        this.accounts = new HashMap<>();
        this.isAdmin = isAdmin;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public HashMap<String, BankAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(BankAccount account) {
    if(!isAdmin){
        if(account instanceof AdministratorAccount){
            System.out.println("You can not create administrator accounts!");
            throw new IllegalArgumentException();
        }
    }
    if(accounts.containsKey(account.getAccountName())){
        throw new IllegalArgumentException();
    }
    accounts.put(account.getAccountName(), account);
}

    public void removeAccount(BankAccount account) {
        if(!accounts.containsValue(account)){
            System.out.println("There is no such account!");
            throw new IllegalArgumentException();
        }
        accounts.remove(account.getAccountName());
    }

    public String getPassword(){
        return this.password;
    }
}
