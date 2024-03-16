package com.example.demo.core.repository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.demo.core.model.BaseEntity;

public abstract class MapRepository<E extends BaseEntity> implements CommonRepository<E, Long> {
    private final Map<Long, E> entities = new TreeMap<>();
    private Long lastId = 0L;

    protected MapRepository() {
    }

    @Override
    public List<E> getAll() {
        return entities.values().stream().toList();
    }

    @Override
    public E get(Long id) {
        return entities.get(id);
    }

    @Override
    public E create(E entity) {
        lastId++;
        entity.setId(lastId);
        entities.put(lastId, entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        if (get(entity.getId()) == null) {
            return null;
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public E delete(E entity) {
        if (get(entity.getId()) == null) {
            return null;
        }
        entities.remove(entity.getId());
        return entity;
    }

    @Override
    public void deleteAll() {
        lastId = 0L;
        entities.clear();
    }
}
