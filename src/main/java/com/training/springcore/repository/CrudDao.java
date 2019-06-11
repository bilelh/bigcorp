package com.training.springcore.repository;

import java.util.List;

public interface CrudDao <T , ID> {

    void persist(T element);

    T findById(ID id);

    List<T> findAll();

    void delete(T element);
}
