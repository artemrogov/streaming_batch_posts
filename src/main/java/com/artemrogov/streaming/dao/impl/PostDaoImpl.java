package com.artemrogov.streaming.dao.impl;

import com.artemrogov.streaming.dao.PostDao;
import com.artemrogov.streaming.domain.Post;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class PostDaoImpl implements PostDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Post create(Post entity) {
       em.persist(entity);
       return entity;
    }

    @Override
    public Post findById(@NonNull Long id) {
        return em.find(Post.class,id);
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> resultQuery = em.createQuery("from Post",Post.class);
        return resultQuery.getResultList();
    }

    @Override
    public Post update(Post entity) {
        return em.merge(entity);
    }

    @Override
    public Post delete(Long id) {
        Post post = findById(id);
        em.remove(post);
        return post;
    }
}
