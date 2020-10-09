package ru.sbt;

import java.util.Collection;

public interface BankEntitiesStorage<V> {
    <T extends V>void save(T entity);

    void saveAll(Collection<? extends V> entities);

    Collection<V> findAll();

    void deleteByKey(Object key);

    void deleteAll(Collection<? extends V> entities);
}
