package main;

public abstract class BankAccount {

    private final String accountName;
    private String accountType;

    public BankAccount(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String nameToString() {
        return "Account Name: " + accountName + " (Type: " + accountType + ")";
    }
    
}