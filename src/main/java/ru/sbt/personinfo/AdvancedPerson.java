package ru.sbt.personinfo;

public class AdvancedPerson {
    private final String firstname;
    private final String lastname;
    private final String sex;
    private final int age;
    private final boolean isMarried;

    public AdvancedPerson(String firstname, String lastname, String sex, int age, boolean isMarried) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.age = age;
        this.isMarried = isMarried;
    }
}
