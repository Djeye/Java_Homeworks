package ru.sbt;

import jdk.incubator.jpackage.internal.Log;

import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private boolean executed = false;
    private boolean rolledBack = false;

    public Transaction(long id, double amount, Account originator, Account beneficiary) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
    }


    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() throws IllegalStateException{
        if (executed) throw new IllegalStateException();
        if (originator != null && beneficiary != null){
            originator.addEntry(new Entry(originator,this, -amount, LocalDateTime.now()));
            beneficiary.addEntry(new Entry(beneficiary,this, amount, LocalDateTime.now()));
            executed = true;
        }else {
            Log.info(" > Account doesn't exists");
            executed = false;
        }
        return this;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() throws IllegalStateException {
        if (!executed || rolledBack) throw new IllegalStateException();
        if (originator != null && beneficiary != null){
            originator.addEntry(new Entry(originator,this, amount, LocalDateTime.now()));
            beneficiary.addEntry(new Entry(beneficiary,this, -amount, LocalDateTime.now()));
            rolledBack = true;
        }else {
            Log.info(" > Account doesn't exists");
            rolledBack = false;
        }
        return this;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Account getOriginator() {
        return originator;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }
}
