package test;

import main.BankAccount;
import main.MainMenu;
import main.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;



public class BankAccountTest {


// I am basically using the code here to test the custom checking their total

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

   
    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }


    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit( 50);
        testAccount.withdraw(30);
        assertEquals(20, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw1() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit( 50);
        testAccount.withdraw(500);
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw2() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.withdraw(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit( 50);
        assertEquals("Deposited: $50.0", testAccount.transactionHistory.get(0));

    }

    @Test
    public void testCreateAccount() {
        MainMenu menu = new MainMenu();

        menu.createAccount();

        assertEquals(2, menu.getNumberOfAccounts());
    }


    @Test
    public void testCloseAccountSize(){
            MainMenu mainMenu = new MainMenu();
            mainMenu.createAccount();
            mainMenu.closeAccount(1);
            assertEquals(1, mainMenu.getAccounts().size());
    }

    @Test
    public void testCloseInvalidAccount(){
            MainMenu mainMenu = new MainMenu();
            mainMenu.createAccount();
            try {
                mainMenu.closeAccount(1);
                fail();
            } catch (IndexOutOfBoundsException e) {
                //do nothing, test passes
            }
    }



    @Test
    public void testTransferMoney() {
        Bank bank = new Bank();

        bank.createAccount();

        bank.performDeposit(1, 5.00);

        bank.performDeposit(2, 1.00);

        bank.transferMoney(1, 2, 3.00);


        assertEquals(2.00, bank.getAccounts().get(1 - 1).getBalance(), 0.01);

    }
    //work in progress


    @Test
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


}


