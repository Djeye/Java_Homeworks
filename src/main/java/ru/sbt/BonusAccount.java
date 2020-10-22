package ru.sbt;


import java.time.LocalDate;
import java.util.Collection;

public class BonusAccount implements Account{
    private final Entries entries;
    private final double cashback;

    public BonusAccount(double cashback) {
        this.cashback = cashback;
        this.entries = new Entries();
    }

    @Override
    public double balanceOn(LocalDate date) {
        double balance = 0;
        for (Entry entry : entries.betweenDates(null, date)) {
            balance += entry.getAmount();
        }
        return balance;
    }

    @Override
    public void addEntry(Entry entryToAccount) {
        entries.addEntry(entryToAccount);
    }

    @Override
    public Collection<Entry> getAllEntries(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    public double getCashback() {
        return cashback;
    }
}
