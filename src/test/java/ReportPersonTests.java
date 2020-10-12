import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.personinfo.Person;
import ru.sbt.reports.ReportGeneratorImpl;
import ru.sbt.reports.ReportImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPersonTests {
    private final List<Person> persons = new ArrayList<Person>();
    private final Map<String, String> updatedNames = new HashMap<>();

    @BeforeEach
    void initialize() {
        Person joe = new Person("Joe", 20);
        Person bob = new Person("Bob", 31);
        Person hue = new Person("Hue", 42);
        Person kate = new Person("Kate", 19);

        persons.add(joe);
        persons.add(bob);
        persons.add(hue);
        persons.add(kate);

        updatedNames.put("age", "Age of person");
        updatedNames.put("name", "Name of person");
    }

    @Test
    void printPersonDefaultInfoTest() {
        ReportGeneratorImpl<Person> generator = new ReportGeneratorImpl<>(Person.class, null);
        ReportImpl expect = generator.generate(persons);

        expect.writeTo(System.out);
    }

    @Test
    void printPersonUpdatedInfoTest() {
        ReportGeneratorImpl<Person> generator = new ReportGeneratorImpl<>(Person.class, updatedNames);
        ReportImpl expect = generator.generate(persons);

        expect.writeTo(System.out);
    }
}
