package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import main.Bank;

import org.junit.jupiter.api.Test;

import junit.framework.AssertionFailedError;

public class BankTest {
    
    @Test
    public void testCloseAccountSize(){
            Bank bank = new Bank();
            bank.createAccount(false, "testAccount", null);
            bank.createAccount(false, "testAccount2", null);
            bank.closeAccount("testAccount2");

            assertEquals(3, bank.getAccounts().size());


    }

    @Test
    public void testCloseInvalidAccount(){
            Bank bank = new Bank();
            bank.createAccount(false, "testAccount", null);
            try {
                bank.closeAccount("testAccount2");
                fail();
            } 
            catch (IllegalArgumentException e) {
                //do nothing, test passes
            }
    }
        


    
}
