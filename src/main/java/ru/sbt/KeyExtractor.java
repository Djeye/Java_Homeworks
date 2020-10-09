package ru.sbt;

public interface KeyExtractor<K, V> {
    <T extends V>K extract(T entity);
}