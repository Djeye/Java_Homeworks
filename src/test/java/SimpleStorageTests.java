import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleStorageTests {
    private final TransactionManager tm = new TransactionManager();
    private Account Joe, Bob, Hue;
    private final SimpleEntitiesStorage<Account> accStorage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
    private final List<Account> accList = new ArrayList<>();
    private final AccountKeyExtractor key = new AccountKeyExtractor();

    @BeforeEach
    void initialize(){
        Joe = new DebitCard(1, tm, null);
        Bob = new DebitCard(2, tm, null);
        Hue = new DebitCard(3, tm, null);
        accList.add(Joe);
        accList.add(Bob);
        accList.add(Hue);
    }


    @Test
    void saveAccountTest(){
        accStorage.save(Joe);

        boolean isSuccess = (1 == accStorage.findAll().size());

        assertTrue(isSuccess);
    }

    @Test
    void deleteAccountTest(){
        accStorage.save(Joe);
        accStorage.deleteByKey(key.extract(Joe));

        boolean isSuccess = (0 == accStorage.findAll().size());

        assertTrue(isSuccess);
    }

    @Test
    void deleteAllAccountTest(){
        accStorage.save(Joe);
        accStorage.save(Bob);
        accStorage.save(Hue);
        accStorage.deleteAll(accList);

        boolean isSuccess = (0 == accStorage.findAll().size());

        assertTrue(isSuccess);
    }
}
