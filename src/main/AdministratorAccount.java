package main;

public class AdministratorAccount extends BankAccount {

    public AdministratorAccount(String accountName,String password) { //administrator should always be created with a password
        super(accountName, password); 
    }

    public void collectFees(BankAccount toAccount,double amount) {

       toAccount.transferMoney(toAccount, amount);      

    }

    public void payInterest(CustomerAccount toAccount ,double interestRate) {

        toAccount.transferMoney(toAccount, toAccount.getBalance() * interestRate);
        
    }

    public void transferMoney(BankAccount toAccount, double transferAmount) {

        //bank vault will transfer money to the account, will implement in vault branch.

        toAccount.receiveTransfer(transferAmount);

    }

    public void receiveTransfer(double transferAmount) {

        //Amount is given to the bank vault, will implement in vault branch.
        
    }


    
}
