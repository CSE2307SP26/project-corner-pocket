package test;

import static org.junit.jupiter.api.Assertions.*;
import main.*;
import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void testCloseAccountSize() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "a", "pw");
        bank.createAccount(false, "b", "pw");

        bank.closeAccount("b");

        assertEquals(2, bank.getAccounts().size()); 
        // includes root + a
    }

    @Test
    public void testCloseInvalidAccount() {
        Bank bank = new Bank(20.00);

        assertThrows(IllegalArgumentException.class, () -> {
            bank.closeAccount("doesNotExist");
        });
    }

    @Test
    public void testCreateAccount() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "test", "pw");

        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    public void testCreateDuplicateAccount() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "test", "pw");

        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount(false, "test", "pw");
        });
    }

    @Test
    public void testCreateCustomerAccountType() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "test", "pw");

        assertTrue(
            bank.getAccounts().get("test") instanceof CustomerAccount
        );
    }

    @Test
    public void testCreateAccountPasswordNullAllowed() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "test", null);

        assertNull(bank.getAccounts().get("test").getPassword());
    }

    @Test
    public void testCreateAdminAccount() {
        Bank bank = new Bank(20.00);

        bank.createAccount(true, "admin1", "pw");

        assertTrue(
            bank.getAccounts().get("admin1") instanceof AdministratorAccount
        );
    }
}