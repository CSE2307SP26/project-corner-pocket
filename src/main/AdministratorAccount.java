package main;

public class AdministratorAccount extends BankAccount {

    private double bankVault;

    public AdministratorAccount(String accountName,String password, double bankVault) { //administrator should always be created with a password
        super(accountName, password); 
        this.bankVault = bankVault;
    }

    public void collectFees(BankAccount fromAccount,double amount) {

       fromAccount.transferMoney(this, amount);      

    }

    public void payInterest(CustomerAccount toAccount , double interestRate) {

        this.transferMoney(toAccount, toAccount.getBalance() * interestRate);
        
    }

    public void transferMoney(BankAccount toAccount, double transferAmount) {

        if(toAccount instanceof CustomerAccount){

           bankVault = bankVault - transferAmount;

           toAccount.receiveTransfer(transferAmount);
        }
        else{
            throw new IllegalArgumentException();
        }

    }

    public void receiveTransfer(double transferAmount) {

        bankVault = bankVault + transferAmount;
        
    }

    public double updateBankVault(){

        return this.bankVault;

    }

    public void updateLocalBankVault(double bankVault){

        this.bankVault = bankVault;

    }

    public void giveLoan(double toAccount, double amount, double interestRate){
        
    }


    
}
