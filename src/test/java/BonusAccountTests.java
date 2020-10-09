import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusAccountTests {
    private final TransactionManager tm = new TransactionManager();
    private DebitCard Joe, Bob, Hue;
    private BonusAccount JoeBonusAccount;


    @BeforeEach
    void initialize(){
        double joeCashback = 0.02d;
        JoeBonusAccount = new BonusAccount(joeCashback);

        Joe = new DebitCard(1, tm, JoeBonusAccount);
        Bob = new DebitCard(2, tm, null);
        Hue = new DebitCard(3, tm, null);

    }

    @Test
    void getBonusesFromWithdraw() {
        Joe.addCash(500);
        Joe.withdraw(200, Bob);

        double bonuses = Joe.getBonuses(null);

        assertEquals(4, bonuses);
    }
}
