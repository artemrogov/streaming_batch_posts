package com.artemrogov.streaming.dao;

import com.artemrogov.streaming.domain.Category;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface CategoryDao extends CrudOperationsDao<Category,Long> {
}
