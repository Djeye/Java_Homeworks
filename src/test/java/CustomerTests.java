import com.company.Customer;

import static org.junit.Assert.*;
import org.junit.Test;


public class CustomerTests {
    Customer cust = new Customer("Johny", "Dee");

    @Test
    public void openAccauntAndCloseAccountPassedTest(){
        // given
        cust.openAccount(12345678);
        // when
        boolean isPassed = cust.closeAccount();
        // then
        assertTrue(isPassed);
    }

    @Test
    public void openAccauntAndCloseItTwiceTest(){
        // given
        cust.openAccount(12345678);
        // when
        cust.closeAccount();
        boolean isPassed = cust.closeAccount();
        // then
        assertFalse(isPassed);
    }

    @Test
    public void checkCorrectFullNameTest(){
        // given
        cust.openAccount(12345678);
        // when
        boolean isPassed = cust.fullName().equals("Johny Dee");
        // then
        assertTrue(isPassed);
    }

    @Test
    public void addMoneyToAccountTest(){
        // given
        cust.openAccount(12345678);
        // when
        boolean isPassed = cust.addMoneyToCurrentAccount(500);
        // then
        assertTrue(isPassed);
    }

    @Test
    public void addMoneyToAccount_thenWithdrawItTest(){
        // given
        cust.openAccount(12345678);
        // when
        cust.addMoneyToCurrentAccount(5000);
        boolean isPassed = cust.withdrawFromCurrentAccount(2500);
        // then
        assertTrue(isPassed);
    }

}
