import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.personinfo.AdvancedPerson;
import ru.sbt.reports.ReportGeneratorImpl;
import ru.sbt.reports.ReportImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReportAdvancedPersonTests {
    private final List<AdvancedPerson> advPersons = new ArrayList<>();
    private final Map<String, String> updatedNames = new HashMap<>();

    @BeforeEach
    void initialize() {
        AdvancedPerson joe = new AdvancedPerson("Joe", "Smith", "male", 20, true);
        AdvancedPerson bob = new AdvancedPerson("Bob", "Adam", "male", 31, false);
        AdvancedPerson hue = new AdvancedPerson("Hue", "Jackson", null, 42, false);
        AdvancedPerson kate = new AdvancedPerson("Kate", "Smith", "female", 19, true);

        advPersons.add(joe);
        advPersons.add(bob);
        advPersons.add(hue);
        advPersons.add(kate);

        updatedNames.put("age", "Age");
        updatedNames.put("firstname", "Name");
        updatedNames.put("sex", "Gender");
        updatedNames.put("isMarried", "Dead inside");
    }

    @Test
    void printAdvancedPersonDefaultInfoTest() {
        ReportGeneratorImpl<AdvancedPerson> generator = new ReportGeneratorImpl<>(AdvancedPerson.class, null);
        ReportImpl expect = generator.generate(advPersons);

        expect.writeTo(System.out);
    }

    @Test
    void printAdvancedPersonUpdatedInfoTest() {
        ReportGeneratorImpl<AdvancedPerson> generator = new ReportGeneratorImpl<>(AdvancedPerson.class, updatedNames);
        ReportImpl expect = generator.generate(advPersons);

        expect.writeTo(System.out);
    }
}