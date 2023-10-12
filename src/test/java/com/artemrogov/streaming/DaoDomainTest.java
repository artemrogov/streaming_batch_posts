package com.artemrogov.streaming;

import com.artemrogov.streaming.domain.Category;
import com.artemrogov.streaming.domain.Post;
import com.artemrogov.streaming.utils.CategoryEntityGenerator;
import com.artemrogov.streaming.utils.PostEntityGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class DaoDomainTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private final static String imageName = "postgres:13-alpine";
    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(imageName);

    private Long postId;
    private Long categoryId;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

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
