package main;

public class AdministratorAccount extends BankAccount {

    private Bank bank;

    public AdministratorAccount(String accountName, Bank bank) {
        super(accountName);
        this.bank = bank;
    }

    public void collectFees(CustomerAccount from, double amount) {
        from.withdraw(amount);
        bank.depositToVault(amount);
    }

    public void payInterest(CustomerAccount account, int rate) {
        if(rate > 100 || rate < 0){
             System.out.println("Invalid interest rate!");
             throw new IllegalArgumentException();
        }
        double interest = account.getBalance() * rate / 100.0;

        if(interest > bank.getBankVaultBalance()){
            System.out.println("Bank vault does not have enough to pay!");
            throw new IllegalArgumentException();
        }

        bank.withdrawFromVault(interest);
        account.deposit(interest);
    }

    public void giveLoan(CustomerAccount account, double amount, int rate) {
        double interest = amount/rate;
        account.setLoanAmount(account.getLoanAmount() + amount + interest);

        bank.withdrawFromVault(amount);
        account.deposit(amount);
    }
}