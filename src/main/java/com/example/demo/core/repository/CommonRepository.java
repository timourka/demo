package com.example.demo.core.repository;

import java.util.List;

public interface CommonRepository<E, T> {
    List<E> getAll();

    E get(T id);

    E create(E entity);

    E update(E entity);

    E delete(E entity);

    void deleteAll();
}
