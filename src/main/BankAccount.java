package main;



public abstract class BankAccount {

  
    private final String accountName;
    private String password;
    private String accountType;
    

    public BankAccount(String accountName, String password) {
        this.accountName = accountName;
        setPassword(password);
    }


    //Sets the passwords based on a passed in string
    public void setPassword(String password) {
        this.password = password;
    }

    //Resets the password to null
    public void resetPassword() {
        this.password = null;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAccountName() { //used for printing the account name in the main menu
        return this.accountName;
    }


    // Gets the current account type
    public String getAccountType() {
        return this.accountType;
    }

    // Sets the current account type 
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    // Used to proces the account information for display in the main menu
    public String nameToString() {
        return "Account Name: " + this.accountName + " (Type: " + this.accountType + ")";
    }

    public abstract void transferMoney(BankAccount toAccount, double transferAmount);

    public abstract void receiveTransfer(double transferAmount);
}
