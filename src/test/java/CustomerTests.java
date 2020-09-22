package test.java;

import main.java.com.company.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTests {

    Customer cust = new Customer("Johny", "Dee");

    @Test
    public void accTests(){
        assertTrue( cust.openAccount(12345678));
        assertFalse(cust.openAccount(12345679));

        assertTrue( cust.closeAccount());
        assertFalse(cust.addMoneyToCurrentAccount(500));

        assertFalse(cust.closeAccount());
        assertFalse(cust.addMoneyToCurrentAccount(500));

        assertTrue(  cust.openAccount(12345678));
        assertEquals(cust.fullName(), "Johny Dee");

        assertTrue( cust.addMoneyToCurrentAccount(500));
        assertFalse(cust.addMoneyToCurrentAccount(-1500));

        assertTrue( cust.withdrawFromCurrentAccount(400));
        assertFalse(cust.addMoneyToCurrentAccount(-2500));
    }
}
