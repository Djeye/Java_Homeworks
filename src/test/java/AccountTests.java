import org.junit.jupiter.api.Test;
import ru.sbt.Account;
import ru.sbt.Entry;
import ru.sbt.Transaction;
import ru.sbt.TransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTests {
    TransactionManager tranMan = new TransactionManager();
    Account joeSmith = new Account(1, tranMan);

    @Test
    void accountTestAddCash(){
        boolean isPassed = joeSmith.addCash(15);

        assertTrue(isPassed);

        boolean isFailed = joeSmith.addCash(-15);

        assertFalse(isFailed);
    }

    @Test
    void accountBalanceTest() {
        boolean isPassed = joeSmith.addCash(500);
        double value = joeSmith.balanceOn(null);

        assertTrue(isPassed);
        assertEquals(500, value);
    }

    @Test
    void accountHistoryTest() {
        Transaction newTrans = new TransactionManager().createTransaction(10, null, null);
        joeSmith.addEntry(new Entry(joeSmith, newTrans, 1, LocalDateTime.now().minusDays(3)));
        joeSmith.addEntry(new Entry(joeSmith, newTrans, 2, LocalDateTime.now().minusDays(1)));
        joeSmith.addEntry(new Entry(joeSmith, newTrans, 3, LocalDateTime.now()));
        joeSmith.addEntry(new Entry(joeSmith, newTrans, 4, LocalDateTime.now().plusDays(3)));

        ArrayList<Entry> checkList = new ArrayList<>(joeSmith.history(LocalDate.now().minusDays(2), LocalDate.now().plusDays(5)));
        Entry firstEntry = checkList.get(0);
        Entry secondEntry = checkList.get(1);

        assertEquals(3, checkList.size());
        assertEquals(2, firstEntry.getAmount());
        assertEquals(3, secondEntry.getAmount());
    }

}

