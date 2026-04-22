package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        CustomerAccount acc = new CustomerAccount("test");
        acc.deposit(50);
        assertEquals(50, acc.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        CustomerAccount acc = new CustomerAccount("test");

        assertThrows(IllegalArgumentException.class, () -> {
            acc.deposit(-50);
        });
    }

    @Test
    public void testWithdraw() {
        CustomerAccount acc = new CustomerAccount("test");
        acc.deposit(50);
        acc.withdraw(30);
        assertEquals(20, acc.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        CustomerAccount acc = new CustomerAccount("test");
        acc.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.withdraw(-10);
        });
    }

    @Test
    public void testTransactionHistory() {
        CustomerAccount acc = new CustomerAccount("test");
        acc.deposit(50);

        assertEquals("Deposited: $50.0",
                acc.getTransactionHistory().get(0));
    }

    @Test
    public void testTransferBetweenCustomers() {
        CustomerAccount a = new CustomerAccount("a");
        CustomerAccount b = new CustomerAccount("b");

        a.deposit(10);
        a.transferTo(b, 10);

        assertEquals(0, a.getBalance(), 0.01);
        assertEquals(10, b.getBalance(), 0.01);
    }



        @Test
    public void testTransferToVault() {
        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(50);

        bank.transferToVault(acc, 50);

        assertEquals(0, acc.getBalance(), 0.01);
        assertEquals(150, bank.getBankVaultBalance(), 0.01);
    }

    @Test
    public void testGiveLoan() {
        Bank bank = new Bank(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get(0);

        CustomerAccount acc = new CustomerAccount("c");

        admin.giveLoan(acc, 50, 10);

        assertEquals(50, acc.getBalance(), 0.01);
        assertEquals(150, bank.getBankVaultBalance(), 0.01);
        assertEquals(55, acc.getLoanAmount(), 0.01);
    }

    @Test
    public void testCollectFees() {
        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get(0);

        admin.collectFees(acc, 20);

        assertEquals(80, acc.getBalance(), 0.01);
        assertEquals(120, bank.getBankVaultBalance(), 0.01);
    }

    @Test
    public void testPayInterest() {
        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get(0);

        admin.payInterest(acc, 10);

        assertEquals(110, acc.getBalance(), 0.01);
        assertEquals(90, bank.getBankVaultBalance(), 0.01);
    }
}