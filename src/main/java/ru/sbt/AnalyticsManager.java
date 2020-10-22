package ru.sbt;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(DebitCard account) {
        if (account == null) return null;

        return transactionManager.findAllTransactionsByAccount(account).stream()
                .map(Transaction::getBeneficiary)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
        if (account == null) return new ArrayList<>();

        return account.getAllEntries(null, null).stream()
                .sorted(Comparator.comparing(Entry::getAmount))
                .filter(e -> e.getAmount() < 0) //  negative amount - purchases
                .limit(10)
                .map(Entry::getTransaction)
                .collect(Collectors.toList());
    }

    public double overallBalanceOfAccounts(List<Account> accounts) {
        if (accounts == null) return 0;

        return accounts.stream()
                .mapToDouble(a -> a.balanceOn(null))
                .sum();
    }

    public <R, T extends Account> Set<R> uniqueKeysOf(List<T> accounts, KeyExtractor<R, Account> extractor) {
        if (accounts == null || extractor == null) return new HashSet<>();

        return accounts.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public <R extends Account> List<R> accountsRangeFrom(List<R> accounts, R minAccount, Comparator<R> comparator) {
        if (accounts == null || comparator == null) return new ArrayList<>();

        return accounts.stream()
                .filter(a -> comparator.compare(a, minAccount) < 0)
                .collect(Collectors.toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<Account> accounts, LocalDate from, LocalDate to) {
        if (accounts == null) return Optional.empty();

        return accounts.stream()
                .map(a -> a.getAllEntries(from, to))
                .flatMap(Collection::stream)  // Get entry as parameter
                .filter(e -> e.getAmount() < 0)
                .min(Comparator.comparing(Entry::getAmount));
    }
}
