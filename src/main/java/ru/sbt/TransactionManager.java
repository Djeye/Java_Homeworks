package ru.sbt;

import jdk.incubator.jpackage.internal.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {

    private final SortedMap<Account, ArrayList<Transaction>> transactionMap = new TreeMap<>();
    private int newTransactionId;

    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        if (originator != null && !transactionMap.containsKey(originator)) {
            transactionMap.put(originator, new ArrayList<>());
        }
        if (beneficiary != null && !transactionMap.containsKey(beneficiary)) {
            transactionMap.put(beneficiary, new ArrayList<>());
        }
        newTransactionId++;
        return new Transaction(newTransactionId, amount, originator, beneficiary);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null || !transactionMap.containsKey(account)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(transactionMap.get(account));
    }


    public void rollbackTransaction(Transaction transaction) {
        if (transaction == null) return;
        try {
            transaction.rollback();
        }catch (IllegalStateException e){
            Log.info(" > Cannot rollback transaction " + transaction.getId() + " error:" + e);
            return;
        }
        if (transaction.getBeneficiary() != null){
            transactionMap.get(transaction.getBeneficiary()).remove(transaction);
        }
        if (transaction.getOriginator() != null){
            transactionMap.get(transaction.getOriginator()).remove(transaction);
        }
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) return;
        try {
            transaction.execute();
        }catch (IllegalStateException e){
            Log.info(" > Cannot execute transaction" + transaction.getId() + " error:" + e);
            return;
        }
        if (transaction.getBeneficiary() != null){
            transactionMap.get(transaction.getBeneficiary()).add(transaction);
        }
        if (transaction.getOriginator() != null){
            transactionMap.get(transaction.getOriginator()).add(transaction);
        }
    }
}

