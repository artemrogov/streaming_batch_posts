package com.artemrogov.streaming.dao;

import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;

@NoRepositoryBean
public interface CrudOperationsDao<T,ID> {
    T create(T entity);

    T findById(@NonNull ID id);

    List<T> findAll();

    T update(T entity);

    T delete(ID id);
}
