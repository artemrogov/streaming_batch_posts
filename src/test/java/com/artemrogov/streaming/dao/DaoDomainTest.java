package com.artemrogov.streaming.dao;

import com.artemrogov.streaming.config.PostgresqlTestContainerConfig;
import com.artemrogov.streaming.domain.Category;
import com.artemrogov.streaming.domain.Post;
import com.artemrogov.streaming.utils.CategoryEntityGenerator;
import com.artemrogov.streaming.utils.PostEntityGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class DaoDomainTest extends PostgresqlTestContainerConfig {

    @Autowired
    private TestEntityManager testEntityManager;

    private Long postId;
    private Long categoryId;

    private List<Post> getMockPostsObjects() {
        return Stream.generate(PostEntityGenerator::generate)
                .limit(5)
                .collect(Collectors.toList());
    }


    private List<Category> getMockCategoriesObjects() {
        return Stream.generate(CategoryEntityGenerator::generate)
                .limit(5)
                .collect(Collectors.toList());
    }

    @BeforeEach
    void setUp() {
        getMockCategoriesObjects().forEach(category -> testEntityManager.persistAndFlush(category));
        getMockPostsObjects().forEach(post -> testEntityManager.persistAndFlush(post));
        TypedQuery<Post> postsTypedQuery = testEntityManager.getEntityManager()
                .createQuery("from Post", Post.class).setMaxResults(1);
        TypedQuery<Category> categoryTypedQuery = testEntityManager.getEntityManager()
                .createQuery("from Category", Category.class).setMaxResults(1);
        Category category = categoryTypedQuery.getSingleResult();
        Post post = postsTypedQuery.getSingleResult();
        this.postId = post.getId();
        this.categoryId = category.getId();
    }

    @Test
    public void testCreate() {
        Post post = PostEntityGenerator.generate();
        testEntityManager.persist(post);
        Assertions.assertNotNull(post.getId());
    }

    @Test
    public void testDetach(){
        Post post = testEntityManager.find(Post.class,postId);
        testEntityManager.detach(post);

        Assertions.assertNotNull(post);

        Assertions.assertFalse(testEntityManager.getEntityManager().contains(post));
    }

    @Test
    public void testMergedDetach(){
        Post post = testEntityManager.find(Post.class,postId);
        testEntityManager.detach(post);

        post.setActive(false);
        Post postUpdated = testEntityManager.merge(post);

        Assertions.assertFalse(postUpdated.getActive());
    }


    @Test
    public void testMerge() {
        Post post = testEntityManager.getEntityManager().getReference(Post.class, postId);
        Category category = testEntityManager.getEntityManager().getReference(Category.class, categoryId);
        post.getCategories().add(category);
        category.getPosts().add(post);
        testEntityManager.merge(category);
        testEntityManager.flush();
        Assertions.assertNotNull(category.getPosts());
    }
}
