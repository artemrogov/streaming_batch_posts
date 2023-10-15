package com.artemrogov.streaming.dao;


import com.artemrogov.streaming.config.PostgresqlTestContainerConfig;
import com.artemrogov.streaming.dao.PostDao;
import com.artemrogov.streaming.dao.impl.PostDaoImpl;
import com.artemrogov.streaming.domain.Post;
import com.artemrogov.streaming.utils.PostEntityGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(PostDaoImpl.class)
public class DaoPost2Test extends PostgresqlTestContainerConfig {

    @Autowired
    private PostDao postDao;

    @Test
    public void createPostTest(){
        Post post = PostEntityGenerator.generate();
        Post postCreated = postDao.create(post);
        Assertions.assertNotNull(postCreated);
    }
}
