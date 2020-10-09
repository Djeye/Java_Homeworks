package ru.sbt;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(DebitCard account) {
        if (account == null) return null;
        Account mostFrequentAccount = null;
        int mostNumerousPurchases = 0;
        Map<Account, Integer> accountsMap = new HashMap<>();

        for (Transaction transaction : transactionManager.findAllTransactionsByAccount(account)) {
            Account beneficiary = transaction.getBeneficiary();
            if (!accountsMap.containsKey(beneficiary)) {
                accountsMap.put(beneficiary, 0);
            }

            Integer countPurchases = accountsMap.get(beneficiary);
            countPurchases++;
            accountsMap.put(beneficiary, countPurchases);

            if (countPurchases > mostNumerousPurchases) {
                mostNumerousPurchases = countPurchases;
                mostFrequentAccount = beneficiary;
            }
        }

        return mostFrequentAccount;
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
        List<Transaction> topTenTransactions = new ArrayList<>();
        if (account == null) return topTenTransactions;
        ArrayList<Transaction> transactions = new ArrayList<>(transactionManager.findAllTransactionsByAccount(account));
        transactions.sort(Comparator.comparing(Transaction::getAmount));

        int maxPurchases;
        if (transactions.size() < 10) {
            System.out.println("There is less then ten purchases");
            maxPurchases = transactions.size();
        } else maxPurchases = 10;

        for (int i = transactions.size() - 1; i > transactions.size() - maxPurchases; i--) {
            if (transactions.get(i).getOriginator() != account) continue;
            topTenTransactions.add(transactions.get(i));
        }
        return topTenTransactions;
    }


    public double overallBalanceOfAccounts(List<Account> accounts) {
        if (accounts == null) return 0;
        double overall = 0d;
        for (Account acc : accounts) {
            overall += acc.balanceOn(null);
        }
        return overall;
    }

    public <R ,T extends Account> Set<R> uniqueKeysOf(List<T> accounts, KeyExtractor<R, Account> extractor) {
        Set<R> keys = new HashSet<>();
        if (accounts == null || extractor == null) return keys;

        for (T entity: accounts) {
            keys.add(extractor.extract(entity));
        }
        return keys;
    }

    public <R extends Account> List<R> accountsRangeFrom(List<R> accounts, R minAccount, Comparator<R> comparator) {
        if (accounts == null || comparator == null) return new ArrayList<>();

        List<R> accountsFromList = new ArrayList<>(accounts);
        accountsFromList.sort(comparator);
        int minIndex;

        if (minAccount == null){
            minIndex = 0;
        }else {
            minIndex = accountsFromList.indexOf(minAccount);
        }

        return accountsFromList.subList(minIndex, accountsFromList.size() - 1);
    }
}
