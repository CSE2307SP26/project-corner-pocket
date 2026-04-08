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
    public void testCreateAccount() {
        Bank bank = new Bank();

        bank.createAccount(false, "testAccount", "password123");

        assertEquals(3, bank.getAccounts().size());
    }

    @Test
    public void testCreateCustomerAccount(){
        Bank bank = new Bank();

        bank.createAccount(false, "testAccount", "password123");

        assertEquals(true, bank.getAccounts().get("testAccount") instanceof CustomerAccount);
    }

    @Test
    public void testCreateCustomerAccountWithoutPassword(){
        
        Bank bank = new Bank();

        bank.createAccount(false, "testAccount", null);

        assertEquals(null, bank.getAccounts().get("testAccount").getPassword());

    }

    @Test
    public void testCreateAdminAccount(){

        Bank bank = new Bank();

        bank.createAccount(true, "testAccount", "password123");

        assertEquals(true, bank.getAccounts().get("testAccount") instanceof AdministratorAccount);


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
        Bank bank = new Bank();
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123");
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");

        adminAccount.transferMoney(customerAccount, 10.00);

        assertEquals(10.00, customerAccount.getBalance(), 0.05);
        assertEquals(10.00, bank.getVault(), 0.05);
    }

    @Test
    public void testTransferFromCustomerToBank(){
        Bank bank = new Bank();
        AdministratorAccount adminAccount = new AdministratorAccount("adminAccount", "password123");
        CustomerAccount customerAccount = new CustomerAccount("customerAccount");

        customerAccount.deposit(20.00);

        customerAccount.transferMoney(adminAccount, 10.00);

        assertEquals(10.00, customerAccount.getBalance(), 0.05);
        assertEquals(10.00, bank.getVault(), 0.05);

    }

    @Test
    public void testTransferFromBankToBank(){ //vault should remain the same in this case

        Bank bank = new Bank();
        AdministratorAccount adminAccount1 = new AdministratorAccount("adminAccount1", "password123");
        AdministratorAccount adminAccount2 = new AdministratorAccount("adminAccount2", "password123");

        adminAccount1.transferMoney(adminAccount2, 10.00);

        assertEquals(20.00, bank.getVault(), 0.05);


    }
    

   /*  @Test
    public void testCollectFees() {
        MainMenu menu = new MainMenu();
        menu.getAccounts().get(0).deposit(100);
        menu.collectFees(1, 10);
        assertEquals(90, menu.getAccounts().get(0).getBalance(), 0.01);
    }

    @Test
    public void testPayInterest() {
        MainMenu menu = new MainMenu();
        menu.getAccounts().get(0).deposit(100);
        menu.payInterest(1, 0.1);;
        assertEquals(110, menu.getAccounts().get(0).getBalance(), 0.01);
    }
        */




}


