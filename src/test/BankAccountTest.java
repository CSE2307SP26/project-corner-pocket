package test;


import main.CustomerAccount;
import main.AdministratorAccount;
import main.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;



public class BankAccountTest {

    @Test
    public void testDeposit() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

   
    @Test
    public void testInvalidDeposit() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }


    @Test
    public void testWithdraw() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.deposit( 50);
        testAccount.withdraw(30);
        assertEquals(20, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw1() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.deposit( 50);
        testAccount.withdraw(500);
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw2() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        try {
            testAccount.withdraw(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTransactionHistory() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.deposit( 50);
        assertEquals("Deposited: $50.0", testAccount.getTransactionHistory().get(0));

    }

    @Test
    public void testPasswordSet() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.setPassword("password123");
        assertEquals("password123", testAccount.getPassword());
    }

    @Test
    public void testPasswordReset() {
        CustomerAccount testAccount = new CustomerAccount("testAccount");
        testAccount.setPassword("password123");
        testAccount.resetPassword();
        assertEquals(null, testAccount.getPassword());
    }


    @Test
    public void testTransferBetweenTwoCustomers() {

        CustomerAccount testAccount1 = new CustomerAccount("testAccount1");

        CustomerAccount testAccount2 = new CustomerAccount("testAccount2");

        testAccount1.deposit(3.00);

        testAccount1.transferMoney(testAccount2, 3.00);

        assertEquals(3.00, testAccount2.getBalance(), 0.05);

    }

    @Test
    public void testTransferFromBankToCustomer(){
        Bank bank = new Bank(20.00);
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 20.00);
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");

        adminAccount.transferMoney(customerAccount, 10.00);
        bank.setBankVaultBalance(adminAccount.updateBankVault());

        assertEquals(10.00, customerAccount.getBalance(), 0.05);
        assertEquals(10.00, bank.getBankVaultBalance(), 0.05);
    }

    @Test
    public void testTransferFromCustomerToBank(){
        Bank bank = new Bank(0.00);
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 0.00);
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");

        customerAccount.deposit(20.00);

        customerAccount.transferMoney(adminAccount, 10.00);
        bank.setBankVaultBalance(adminAccount.updateBankVault());

        assertEquals(10.00, customerAccount.getBalance(), 0.05);
        assertEquals(10.00, bank.getBankVaultBalance(), 0.05);

    }

    @Test
    public void testTransferFromVaultToVault(){ //vault should remain the same in this case

        AdministratorAccount adminAccount1 = new AdministratorAccount("adminAccount1", "password123", 20.00);
        AdministratorAccount adminAccount2 = new AdministratorAccount("adminAccount2", "password123", 20.00);
        try{
            adminAccount1.transferMoney(adminAccount2, 10.00);
            fail();
        }
        catch(IllegalArgumentException e){
            //do nothing test passes
        }
        

    }
    

   @Test
    public void testCollectFees() {
        Bank bank = new Bank(20.00);
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 20.00);
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");
        customerAccount.deposit(20.00);
        adminAccount.collectFees(customerAccount, 10.00);
        bank.setBankVaultBalance(adminAccount.updateBankVault());
        assertEquals(10.00, customerAccount.getBalance(), 0.05);
        assertEquals(30.00, bank.getBankVaultBalance(), 0.05);
        
    }

   @Test
    public void testPayInterest() {
        Bank bank = new Bank(20.00);
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123", 20.00);
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");
        customerAccount.deposit(100.00);
        adminAccount.payInterest(customerAccount, 0.10);
        bank.setBankVaultBalance(adminAccount.updateBankVault());
        assertEquals(110.00, customerAccount.getBalance(), 0.05);
        assertEquals(10.00, bank.getBankVaultBalance(), 0.05); 
    }
    




}


