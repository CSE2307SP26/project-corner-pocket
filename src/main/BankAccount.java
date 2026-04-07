package main;
import java.util.ArrayList;



public abstract class BankAccount {

  
    private final String accountName;
    private String password;
    

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

    public abstract void transferMoney(BankAccount toAccount, double transferAmount);

    public abstract void receiveTransfer(double transferAmount);
}
