package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class BankTest {

    @Test
    public void testCreateUserValid() {
        Bank bank = new Bank(1000);

        bank.createUser("alice", "pass123", false, 18);

        User user = bank.getUser("alice");

        assertNotNull(user);
        assertEquals("alice", user.getUsername());
    }

    @Test
    public void testCreateUserDuplicateThrowsException() {
        Bank bank = new Bank(1000);

        bank.createUser("bob", "pass", false, 18);

        try {
            bank.createUser("bob", "pass2", false, 18);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User exists", e.getMessage());
        }
    }

    @Test
    public void testDepositToVaultValid() {
        Bank bank = new Bank(500);

        bank.depositToVault(200);

        assertEquals(700, bank.getBankVaultBalance(), 0.0001);
    }

    @Test
    public void testDepositToVaultInvalid() {
        Bank bank = new Bank(500);

        try {
            bank.depositToVault(-50);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testWithdrawFromVaultValid() {
        Bank bank = new Bank(500);

        bank.withdrawFromVault(200);

        assertEquals(300, bank.getBankVaultBalance(), 0.0001);
    }

    @Test
    public void testWithdrawFromVaultInsufficientFunds() {
        Bank bank = new Bank(100);

        try {
            bank.withdrawFromVault(500);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient vault funds", e.getMessage());
        }
    }

    @Test
    public void testTransferInvalidAmount() {
        Bank bank = new Bank(1000);

        CustomerAccount acc1 = new CustomerAccount("Checking");
        CustomerAccount acc2 = new CustomerAccount("Checking");

        try {
            bank.transfer(acc1, acc2, -10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testTransferToVaultValid() {
        Bank bank = new Bank(500);

        User user = new User("user", "pass", false, 18);

        CustomerAccount acc = new CustomerAccount("Checking");
        acc.deposit(300);

        bank.transferToVault(acc, user,  200);

        assertEquals(100, acc.getBalance(), 0.0001);
        assertEquals(700, bank.getBankVaultBalance(), 0.0001);
    }

    @Test
    public void testCanWithdrawInvalidAge() {

        Bank bank = new Bank(500);
        CustomerAccount acc = new CustomerAccount("test");
        acc.deposit(50);
        User user = new User("user1", "password123", false, 17);
        
        assertEquals(false, bank.canWithdraw(acc, user));

    }

    @Test
    public void testTransferToVaultInvalid() {
        Bank bank = new Bank(500);

        User user = new User("user", "pass", false, 18);

        CustomerAccount acc = new CustomerAccount("Checking");

        try {
            bank.transferToVault(acc, user, -50);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
public void testCanWithdrawValid() {

    Bank bank = new Bank(1000);

    CustomerAccount acc = new CustomerAccount("acc");
    acc.setAccountType("Normal Account");

    User user = new User("user", "pass", false, 20);

    assertTrue(bank.canWithdraw(acc, user));
}

@Test
public void testCanWithdrawEducationalAccountBlocked() {

    Bank bank = new Bank(1000);

    CustomerAccount acc = new CustomerAccount("acc");
    acc.setAccountType("Educational Account");

    User user = new User("user", "pass", false, 20);

    assertFalse(bank.canWithdraw(acc, user));
}

@Test
public void testCanWithdrawAdminAccountBlocked() {

    Bank bank = new Bank(1000);

    AdministratorAccount acc = new AdministratorAccount("admin", bank);

    User user = new User("user", "pass", false, 20);

    assertFalse(bank.canWithdraw(acc, user));
}

@Test
public void testCanTransferValid() {

    Bank bank = new Bank(1000);

    CustomerAccount from = new CustomerAccount("from");
    from.setAccountType("Normal Account");

    CustomerAccount to = new CustomerAccount("to");
    to.setAccountType("Normal Account");

    assertTrue(bank.canTransfer(from, to));
}

@Test
public void testCanTransferInvestmentAccountFromBlocked() {

    Bank bank = new Bank(1000);

    CustomerAccount from = new CustomerAccount("from");
    from.setAccountType("Investment Account");

    CustomerAccount to = new CustomerAccount("to");
    to.setAccountType("Normal Account");

    assertFalse(bank.canTransfer(from, to));
}

@Test
public void testCanTransferEducationalAccountToBlocked() {

    Bank bank = new Bank(1000);

    CustomerAccount from = new CustomerAccount("from");
    from.setAccountType("Normal Account");

    CustomerAccount to = new CustomerAccount("to");
    to.setAccountType("Educational Account");

    assertFalse(bank.canTransfer(from, to));
}


}
