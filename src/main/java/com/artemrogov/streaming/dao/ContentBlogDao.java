package com.artemrogov.streaming.dao;

import com.artemrogov.streaming.dto.ContentDataDto;
import com.artemrogov.streaming.entities.Category;
import com.artemrogov.streaming.entities.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class ContentBlogDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<ContentDataDto> getData(){
        return null;
    }
}
