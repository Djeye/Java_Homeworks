package ru.sbt;

import java.util.*;

public class SimpleEntitiesStorage<V> implements BankEntitiesStorage<V> {
    private final Map<Object, V> storage = new HashMap<>();
    private final KeyExtractor<? , ? super V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<? , ? super V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public <T extends V>void save(T entity){
        if (entity == null) return;

        storage.put(keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(Collection<? extends V> entities) {
        if (entities == null) return;

        for (V entity:entities) {
            save(entity);
        }
    }

    @Override
    public Collection<V> findAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteByKey(Object key) {
        if (key ==null) return;

        storage.remove(key);
    }

    @Override
    public void deleteAll(Collection<? extends V> entities) {
        if (entities == null) return;

        for (V entity: entities) {
            deleteByKey(keyExtractor.extract(entity));
        }
    }
}