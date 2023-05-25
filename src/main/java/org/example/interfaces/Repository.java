package org.example.interfaces;

import java.util.List;

public interface Repository<T> {
    boolean create(T o);

    boolean update(T o);

    boolean delete(Long id);

    T findById(Long id);

    List<T> findAll();
}
