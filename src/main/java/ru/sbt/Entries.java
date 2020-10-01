package ru.sbt;


import jdk.incubator.jpackage.internal.Log;

import java.time.LocalDate;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private SortedMap<LocalDate, ArrayList<Entry>> entriesMap = new TreeMap<>();

    void addEntry(Entry entry) {
        if (entry == null) return;
        LocalDate entryTime = entry.getTime().toLocalDate();
        if (!entriesMap.containsKey(entryTime)) {
            entriesMap.put(entryTime, new ArrayList<>());
        }
        // add entry by key
        Log.info(" > Successful add Entry");
        entriesMap.get(entryTime).add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        if (date == null && !entriesMap.containsKey(date)) {
            Log.info("There is no your Entry");
            return new ArrayList<>();
        }
        return new ArrayList<>(entriesMap.get(date));
    }
    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        ArrayList<Entry> betweenDatesEntries = new ArrayList<>();
        for (Map.Entry<LocalDate, ArrayList<Entry>> entry:entriesMap.entrySet()) {
            if (entry.getKey().isAfter(from) && entry.getKey().isBefore(to)){
                betweenDatesEntries.addAll(entry.getValue());
            }
        }
        return betweenDatesEntries;
    }

    Entry last() {
        if (entriesMap.isEmpty()) return null;
        //array of entries in last datetime
        ArrayList<Entry> lastEntries = entriesMap.get(entriesMap.lastKey());
        //return last in that list
        return lastEntries.get(lastEntries.size()-1);
    }
}
