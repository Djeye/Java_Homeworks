import com.company.Account;

import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTests {
    @Test
    public void addPositivePassedTest(){
        // given
        Account account = new Account(4635235);
        // when
        boolean isPassed = account.add(1500);
        // then
        assertTrue(isPassed);

    }

    @Test
    public void addNegativePassedTest(){
        // given
        Account account = new Account(453646);
        // when
        boolean isPassed = account.add(-1000);
        // then
        assertFalse(isPassed);
    }

    @Test
    public void withdrawPositive_lessThanAdd_PassedTest(){
        // given
        Account account = new Account(4635235);
        // when
        account.add(1500);
        boolean isPassed = account.withdraw(500);
        // then
        assertTrue(isPassed);
    }

    @Test
    public void withdrawPositive_moreThanAdd_PassedTest(){
        // given
        Account account = new Account(4635235);
        // when
        account.add(1000);
        boolean isPassed = account.withdraw(5000);
        // then
        assertFalse(isPassed);
    }
}
