package test.java;

import main.java.com.company.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTests {
    Account acc = new Account(1233456);

    @Test
    public void accTests(){
        assertTrue(acc.add(1500));

        assertFalse(acc.add(-1000));

        assertTrue(acc.withdraw(500));

        assertFalse(acc.withdraw(5000));
    }
}
