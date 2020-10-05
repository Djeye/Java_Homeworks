import com.company.Customer;

import static org.junit.Assert.*;
import org.junit.Test;


public class CustomerTests {
    Customer customer = new Customer("Johny", "Dee");

    @Test
    public void openAccauntAndCloseAccountPassedTest(){
        // given
        customer.openAccount(12345678);
        // when
        boolean isPassed = customer.closeAccount();
        // then
        assertTrue(isPassed);
    }

    @Test
    public void openAccauntAndCloseItTwiceTest(){
        // given
        customer.openAccount(12345678);
        customer.closeAccount();
        // when
        boolean isPassed = customer.closeAccount();
        // then
        assertFalse(isPassed);
    }

    @Test
    public void checkCorrectFullNameTest(){
        // given
        customer.openAccount(12345678);
        // when
        boolean isPassed = customer.fullName().equals("Johny Dee");
        // then
        assertTrue(isPassed);
    }

    @Test
    public void addMoneyToAccountTest(){
        // given
        customer.openAccount(12345678);
        // when
        boolean isPassed = customer.addMoneyToCurrentAccount(500);
        // then
        assertTrue(isPassed);
    }

    @Test
    public void withdrawFromAccountTest(){
        // given
        customer.openAccount(12345678);
        customer.addMoneyToCurrentAccount(5000);
        // when
        boolean isPassed = customer.withdrawFromCurrentAccount(2500);
        // then
        assertTrue(isPassed);
    }
}