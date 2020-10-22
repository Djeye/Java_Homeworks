package ru.sbt;

import java.time.LocalDate;
import java.util.Collection;

public interface Account {

    double balanceOn(LocalDate date);

    void addEntry(Entry entryToAccount);

    Collection<Entry> getAllEntries(LocalDate from, LocalDate to);
}
