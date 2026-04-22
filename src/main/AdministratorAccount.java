package main;

public class AdministratorAccount extends BankAccount {

    private Bank bank;

    public AdministratorAccount(String accountName, Bank bank) {
        super(accountName);
        this.bank = bank;
    }

    public void collectFees(CustomerAccount from, double amount) {
        bank.transfer(from, null, amount);
        bank.depositToVault(amount);
    }

    public void payInterest(CustomerAccount account, int rate) {
        double interest = account.getBalance() * rate / 100.0;

        bank.withdrawFromVault(interest);
        account.deposit(interest);
    }

    public void giveLoan(CustomerAccount account, double amount, int rate) {
        account.setLoanAmount(account.getLoanAmount() + amount);

        bank.withdrawFromVault(amount);
        account.deposit(amount);
    }
}