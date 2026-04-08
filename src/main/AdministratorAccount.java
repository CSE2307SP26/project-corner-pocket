package main;

public class AdministratorAccount extends BankAccount {

    private Bank bank;

    public AdministratorAccount(String accountName,String password, Bank bank) { //administrator should always be created with a password
        super(accountName, password); 
        this.bank = bank;
    }

    public void collectFees(BankAccount fromAccount,double amount) {

       fromAccount.transferMoney(this, amount);      

    }

    public void payInterest(CustomerAccount toAccount ,double interestRate) {

        this.transferMoney(toAccount, toAccount.getBalance() * interestRate);
        
    }

    public void transferMoney(BankAccount toAccount, double transferAmount) {

        bank.setBankVaultBalance(bank.getBankVaultBalance() - transferAmount);

        toAccount.receiveTransfer(transferAmount);

    }

    public void receiveTransfer(double transferAmount) {

        bank.setBankVaultBalance(bank.getBankVaultBalance() + transferAmount);

    }

    public void giveLoan(double toAccount, double amount, double interestRate){
        
    }


    
}
