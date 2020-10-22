import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsManagerTests {
    private final int START_CASH_ONE = 1000;
    private final int START_CASH_TWO = 3000;


    private DebitCard joeSmith;
    private DebitCard adamEdler;
    private final TransactionManager transactionManager = new TransactionManager();
    private final AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);

    @BeforeEach
    void preparationSet() {
        joeSmith = new DebitCard(1, transactionManager, null);
        adamEdler = new DebitCard(2, transactionManager, null);
        joeSmith.addCash(START_CASH_ONE);
        adamEdler.addCash(START_CASH_TWO);
        joeSmith.withdraw(500, adamEdler);
        joeSmith.withdraw(200, adamEdler);
        adamEdler.withdraw(100, joeSmith);
    }

    @Test
    void analyticsManagerCorrectAccountsTest() {
        Account acc = analyticsManager.mostFrequentBeneficiaryOfAccount(joeSmith);
        boolean isPassed = acc == adamEdler;

        assertFalse(isPassed);

        acc = analyticsManager.mostFrequentBeneficiaryOfAccount(adamEdler);
        isPassed = acc == adamEdler;

        assertTrue(isPassed);

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

    @Test
    void calcOverallAllAccountsTest(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(joeSmith);
        accountList.add(adamEdler);

        double actualBalance = analyticsManager.overallBalanceOfAccounts(accountList);

        assertEquals(START_CASH_ONE + START_CASH_TWO, actualBalance);
    }

    @Test
    void findAllUniqueKeysTest(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(joeSmith);
        accountList.add(adamEdler);

        Set<Integer> keys = analyticsManager.uniqueKeysOf(accountList, new AccountKeyExtractor());

        boolean isSuccess = (keys.size() == 2);

        assertTrue(isSuccess);
    }
}
