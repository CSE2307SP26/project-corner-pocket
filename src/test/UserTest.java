package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class UserTest {

    @Test

    public void testCreateUser(){

        User user = new User("user1", "password123", false);

        assertEquals("user1", user.getUsername());
        assertEquals("password123", user.getPassword());

    }

    @Test

    public void testSetPassword(){

        User user = new User("user1", "password123", false);

        user.setPassword("abcdefg");

        assertEquals("abcdefg", user.getPassword());

    }

    @Test

    public void testCheckPassword(){

        User user = new User("user1", "password123", false);


        assertEquals(true, user.checkPassword("password123") );

    }

    @Test

    public void testInvalidCheckPassword(){

        User user = new User("user1", "password123", false);

        assertEquals(false, user.checkPassword("abcdefg"));

    }

    @Test

    public void testAddAccount(){
        User user = new User("user1", "password123", false);

        BankAccount account = new CustomerAccount("customeraccount1");

        user.addAccount(account);

        assertEquals(1, user.getAccounts().size());
        assertEquals(account, user.getAccounts().get("customeraccount1"));

    }

    @Test

    public void testAddDuplicateUsernameAccounts(){

         User user = new User("user1", "password123", false);

        BankAccount account = new CustomerAccount("customeraccount1");
        
        BankAccount account2 = new CustomerAccount("customeraccount1");

        user.addAccount(account);

        try{
            user.addAccount(account2);
            fail();
        }
        catch(Exception e){
            //test passes
        }
    }

    @Test

    public void testAddSuccessfulAdminAccount(){

        Bank bank = new Bank(10.0);

        User user = new User("admin1", "password123", true);

        BankAccount account = new AdministratorAccount("adminaccount1", bank);

        user.addAccount(account);

        assertEquals(1, user.getAccounts().size());

    }

    @Test

    public void testAddAdminAccountAsCustomer(){

        Bank bank = new Bank(10.0);

        User user = new User("admin1", "password123", false);

        BankAccount account = new AdministratorAccount("adminaccount1", bank);

        try{
            user.addAccount(account);
            fail();
        }
        catch(Exception e){
            //test passes
        }


    }


    @Test

    public void testRemoveAccount(){

        User user = new User("user1", "password123", false);

        BankAccount account = new CustomerAccount("customeraccount1");

        user.addAccount(account);

        user.removeAccount(account);

        assertEquals(0, user.getAccounts().size());

    }

    @Test

    public void testRemoveNonExistingAccount(){

        User user = new User("user1", "password123", false);

        BankAccount account = new CustomerAccount("customeraccount1");

        try{
            user.removeAccount(account);
            fail();
        }
        catch(Exception e){
            //test passes
        }
        


    }

    
}
