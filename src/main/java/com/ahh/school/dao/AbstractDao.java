package main.java.com.ahh.school.dao;

import java.util.List;

public interface AbstractDao<T, ID extends Comparable<ID>>{
    List<T> fetchAll();
    List<T> fetchAllByName(String name);

    boolean save(T entity);
    boolean delete(T entity);
    boolean deleteById(ID Id);

    T update(T entity);
    T findOne(ID id);


}
