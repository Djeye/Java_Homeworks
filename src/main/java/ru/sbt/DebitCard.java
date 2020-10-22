package ru.sbt;

import java.time.LocalDate;
import java.util.Collection;

public class DebitCard implements Account{
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;
    private final BonusAccount bonusAccount;

    public DebitCard(long id, TransactionManager transactionManager, BonusAccount bonusAccount) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.bonusAccount = bonusAccount;
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
    public boolean withdraw(double amount, DebitCard beneficiary) {
        if (amount <= 0 || balanceOn(null) - amount < 0) {
            return false;
        }
        Transaction withdrawTransaction = transactionManager.createTransaction(amount, this, beneficiary);

        if (bonusAccount != null) {
            Transaction bonusTransaction = transactionManager.createTransaction(amount * bonusAccount.getCashback(), null, bonusAccount);
            transactionManager.executeTransaction(bonusTransaction);
        }
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
    @Override
    public void addEntry(Entry entryToAccount) {
        entries.addEntry(entryToAccount);
    }

    @Override
    public Collection<Entry> getAllEntries(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
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
    @Override
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

    public double getBonuses(LocalDate date) {
        if (bonusAccount != null) {
            return bonusAccount.balanceOn(date);
        } else {
            return 0d;
        }
    }
}

