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

        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> postCriteriaQuery = criteria.createQuery(Post.class);
        Root<Post> postRoot = postCriteriaQuery.from(Post.class);
        Join<Post, Category> jPostCategories = postRoot.join("categories");

        TypedQuery<Post> postQuery = entityManager.createQuery(postCriteriaQuery);

        return postQuery.getResultList().stream().map(p-> {
            ContentDataDto content = new ContentDataDto();
            content.setId(p.getId());
            content.setTitle(p.getTitle());
            List<Long> ids = p.getCategories().stream().map(Category::getId).collect(Collectors.toList());
            content.setCategoriesIds(ids);
            return content;
        }).collect(Collectors.toList());

    }
}
