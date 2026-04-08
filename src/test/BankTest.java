package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import main.AdministratorAccount;
import main.Bank;
import main.CustomerAccount;

import org.junit.jupiter.api.Test;

import junit.framework.AssertionFailedError;

public class BankTest {
    
    @Test
    public void testCloseAccountSize(){
            Bank bank = new Bank(20.00);
            bank.createAccount(false, "testAccount", null);
            bank.createAccount(false, "testAccount2", null);
            bank.closeAccount("testAccount2");

            assertEquals(2, bank.getAccounts().size());

    }

    @Test
    public void testCloseInvalidAccount(){
            Bank bank = new Bank(20.00);
            bank.createAccount(false, "testAccount", null);
            try {
                bank.closeAccount("testAccount2");
                fail();
            } 
            catch (IllegalArgumentException e) {
                //do nothing, test passes
            }
    }

    @Test
    public void testCreateAccount() {
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "testAccount", "password123");

        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    public void testCreateAccountWithDuplicateUsername(){

        Bank bank = new Bank(20.00);
        try{
            bank.createAccount(false, "testAccount", null);
            bank.createAccount(false, "testAccount", null);
            fail();
        }
        catch(IllegalArgumentException e){
            //do nothing, test passes
        }
    }

    @Test
    public void testCreateCustomerAccount(){
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "testAccount", "password123");

        assertEquals(true, bank.getAccounts().get("testAccount") instanceof CustomerAccount);
    }

    @Test
    public void testCreateCustomerAccountWithoutPassword(){
        
        Bank bank = new Bank(20.00);

        bank.createAccount(false, "testAccount", null);

        assertEquals(null, bank.getAccounts().get("testAccount").getPassword());

    }

    @Test
    public void testCreateAdminAccount(){

        Bank bank = new Bank(20.00);

        bank.createAccount(true, "testAccount", "password123");

        assertEquals(true, bank.getAccounts().get("testAccount") instanceof AdministratorAccount);


    }

    @Test
    public void testTwoOppositeTransfers(){

        Bank bank = new Bank(20.00);
        bank.createAccount(true, "adminAccount", "password123");
        bank.createAccount(false, "customerAccount")



    }





        


    
}
