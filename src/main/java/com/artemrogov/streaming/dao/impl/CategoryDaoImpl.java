package com.artemrogov.streaming.dao.impl;

import com.artemrogov.streaming.dao.CategoryDao;
import com.artemrogov.streaming.domain.Category;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public Category create(Category entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Category findById(@NonNull Long id) {
        return em.find(Category.class,id);
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = em.createQuery("from Category",Category.class);
        return query.getResultList();
    }

    @Override
    public Category update(Category entity) {
        return em.merge(entity);
    }

    @Override
    public Category delete(Long id) {
        Category category = findById(id);
        em.remove(category);
        return category;
    }
}
