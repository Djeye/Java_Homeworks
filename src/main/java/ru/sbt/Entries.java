package ru.sbt;

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
        entriesMap.get(entryTime).add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        if (date == null && !entriesMap.containsKey(date)) {
            System.out.println("There is no your Entry");
            return new ArrayList<>();
        }
        return new ArrayList<>(entriesMap.get(date));
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        ArrayList<Entry> betweenDatesEntries = new ArrayList<>();
        SortedMap<LocalDate, ArrayList<Entry>> partOfMap;

        if (from == null && to == null) {
            partOfMap = entriesMap;
        }
        else if (from == null) {
            partOfMap = entriesMap.headMap(to);
        }
        else if (to == null) {
            partOfMap = entriesMap.tailMap(from);
        }
        else {
            partOfMap = entriesMap.subMap(from, to);
        }

        for(Map.Entry<LocalDate, ArrayList<Entry>> entry: partOfMap.entrySet()) {
            betweenDatesEntries.addAll(entry.getValue());
        }

        return betweenDatesEntries;
    }

    Entry last() {
        if (entriesMap.isEmpty()) return null;
        //array of entries in last datetime
        ArrayList<Entry> lastEntries = entriesMap.get(entriesMap.lastKey());
        //return last in that list
        return lastEntries.get(lastEntries.size() - 1);
    }
}
