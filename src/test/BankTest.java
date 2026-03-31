package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import main.BankAccount;
import main.MainMenu;
import main.Bank;

public class BankTest {
    
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
            try {
                mainMenu.closeAccount(2);
                fail();
            } catch (IndexOutOfBoundsException e) {
                //do nothing, test passes
            }
    }


    
}
