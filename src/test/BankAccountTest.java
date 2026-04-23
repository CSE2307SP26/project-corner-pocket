package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        Bank bank = new Bank(50.0);
        CustomerAccount a = new CustomerAccount("a");
        CustomerAccount b = new CustomerAccount("b");

        a.deposit(10);
        bank.transfer(a, b, 10);

        assertEquals(0, a.getBalance(), 0.01);
        assertEquals(10, b.getBalance(), 0.01);
    }

    @Test
    public void testBalanceLow() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CustomerAccount a = new CustomerAccount("a");
        a.deposit(10);

        a.warnUser();

        System.setOut(System.out); // restore stdout
        assertEquals("WARNING: account balance low.", outputStream.toString().trim());
        
    }



    @Test
    public void testGiveLoan() {
        Bank bank = new Bank(100);

        User adminUser = bank.getUser("root");

        CustomerAccount acc = new CustomerAccount("c");

        ((AdministratorAccount) adminUser.getAccounts().get("root")).giveLoan(acc, 50, 10);

        assertEquals(50, acc.getBalance(), 0.01);
        assertEquals(50, bank.getBankVaultBalance(), 0.01);
        assertEquals(55, acc.getLoanAmount(), 0.01);
    }

    @Test
    public void testCollectFees() {
        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get("root");

        admin.collectFees(acc, 20);

        assertEquals(80, acc.getBalance(), 0.01);
        assertEquals(120, bank.getBankVaultBalance(), 0.01);
    }

    @Test
    public void testPayInterest() {
        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.setAccountType("Normal");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get("root");

        admin.payInterest(acc, 10);

        assertEquals(110, acc.getBalance(), 0.01);
        assertEquals(90, bank.getBankVaultBalance(), 0.01);
    }

    @Test
    public void testInvalidPayInterestWithInvalidRate(){

        Bank bank = new Bank(100);

        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get("root");

        try{
            admin.payInterest(acc, 1000);
            fail();
        }
        catch(Exception e){
            //test passes
        }

    }

    @Test
    public void testInvalidPayInterestGreaterThanBankVault(){

   @Test
   public void testPayInterest() {
       Bank bank = new Bank(20.00);
       AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 20.00);
       CustomerAccount customerAccount = new CustomerAccount("customerAccount");
       customerAccount.deposit(100.00);
       adminAccount.payInterest(customerAccount, 10);
       bank.setBankVaultBalance(adminAccount.updateBankVault());
       assertEquals(110.00, customerAccount.getBalance(), 0.05);
       assertEquals(10.00, bank.getBankVaultBalance(), 0.05);
   }
    
   @Test
   public void testPayLoan() {

       Bank bank = new Bank(20.00);
       AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 20.00);
       CustomerAccount customerAccount = new CustomerAccount("customerAccount");
       customerAccount.deposit(100.00);
       customerAccount.setLoanAmount(20.00);
       customerAccount.payLoan(adminAccount, 20.00);
       bank.setBankVaultBalance(adminAccount.updateBankVault());
       assertEquals(80.00, customerAccount.getBalance(), 0.05);
       assertEquals(0.00, customerAccount.getLoanAmount(), 0.05);
       assertEquals(40.00, bank.getBankVaultBalance(), 0.05);
   }

  @Test
   public void testGiveLoan() {

       Bank bank = new Bank(100.00);
       AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123",100.00);
       CustomerAccount customerAccount = new CustomerAccount("customerAccount");
       customerAccount.deposit(100.00);
       adminAccount.giveLoan(customerAccount, 50.00, 8);
       bank.setBankVaultBalance(adminAccount.updateBankVault());
       assertEquals(146.00, customerAccount.getBalance(), 0.05);
       assertEquals(54.00, bank.getBankVaultBalance(), 0.05);
       assertEquals(50.00, customerAccount.getLoanAmount(), 0.05);
   }


        CustomerAccount acc = new CustomerAccount("c");
        acc.deposit(100);

        User adminUser = bank.getUser("root");
        AdministratorAccount admin =
                (AdministratorAccount) adminUser.getAccounts().get("root");

        try{
            admin.payInterest(acc, 20);
            fail();
        }
        catch(Exception e){
            //test passes
        }

    }
}
