package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class MenuTest {


    @Test
    public void testLoginSuccessSetsUser() {

        Bank bank = new Bank(10000);
        bank.createUser("alice", "pass", false, 20);

        User user = bank.getUser("alice");

        assertNotNull(user);
        assertTrue(user.checkPassword("pass"));
    }

    @Test
    public void testLoginFailsWrongPassword() {

        Bank bank = new Bank(10000);
        bank.createUser("bob", "secret", false, 22);

        User user = bank.getUser("bob");

        assertFalse(user.checkPassword("wrongpass"));
    }


    @Test
    public void testLoginFailsUserNotFound() {

        Bank bank = new Bank(10000);

        User user = bank.getUser("ghost");

        assertNull(user);
    }


}