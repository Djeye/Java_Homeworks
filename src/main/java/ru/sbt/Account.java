package ru.sbt;

import java.time.LocalDate;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    public Account(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        if (amount <= 0 || balanceOn(null) - amount < 0) {
            return false;
        }
        Transaction withdrawTransaction = transactionManager.createTransaction(amount, this, beneficiary);
        transactionManager.executeTransaction(withdrawTransaction);
        return true;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        return withdraw(amount, null);
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount <= 0) return false;
        Transaction addCashTransaction = transactionManager.createTransaction(amount, null, this);
        transactionManager.executeTransaction(addCashTransaction);
        return true;
    }

    /**
     * Adds entry to account.
     */
    public void addEntry(Entry entryToAccount) {
        entries.addEntry(entryToAccount);
    }


    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        double balance = 0;
        for (Entry entry : entries.betweenDates(null, date)) {
            balance += entry.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        Entry lastEntry = entries.last();
        if (lastEntry == null) return;
        transactionManager.rollbackTransaction(lastEntry.getTransaction());
    }
}

