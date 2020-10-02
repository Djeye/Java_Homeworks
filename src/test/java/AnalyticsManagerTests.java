import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.Account;
import ru.sbt.AnalyticsManager;
import ru.sbt.Transaction;
import ru.sbt.TransactionManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsManagerTests {
    private Account joeSmith;
    private Account adamEdler;
    private final TransactionManager transactionManager = new TransactionManager();
    private final AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);

    @BeforeEach
    void preparationSet() {
        joeSmith = new Account(1, transactionManager);
        adamEdler = new Account(2, transactionManager);
        joeSmith.addCash(1000);
        adamEdler.addCash(300);
        joeSmith.withdraw(200, adamEdler);
        joeSmith.withdraw(500, adamEdler);
        adamEdler.withdraw(100, joeSmith);
    }

    @Test
    void analyticsManagerCorrectAccountsTest() {
        Account acc = analyticsManager.mostFrequentBeneficiaryOfAccount(joeSmith);
        boolean isPassed = acc == adamEdler;

        assertTrue(isPassed);

        acc = analyticsManager.mostFrequentBeneficiaryOfAccount(adamEdler);
        isPassed = acc == adamEdler;

        assertTrue(isPassed);

    }

    @Test
    void analyticsManagerTopTenZeroTest() {
        ArrayList<Transaction> topTransactions = new ArrayList<>(analyticsManager.topTenExpensivePurchases(adamEdler));

        assertEquals(0, topTransactions.size());
    }

    @Test
    void analyticsManagerTopTenExperimentTest() {
        ArrayList<Transaction> topTransactions = new ArrayList<>(analyticsManager.topTenExpensivePurchases(joeSmith));
        double trans1 = topTransactions.get(0).getAmount();
        double trans2 = topTransactions.get(1).getAmount();

        assertEquals(2, topTransactions.size());
        assertEquals(500, trans1);
        assertEquals(200, trans2);
    }
}
