package com.example.fxtest_02.dao;

import java.util.List;

public interface DAO<T, U> {
    void save(T o);
    void removeById(U id);
    List<T> getAll();
    void update(T o);
}
