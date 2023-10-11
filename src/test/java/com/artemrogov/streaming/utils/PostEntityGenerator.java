package com.artemrogov.streaming.utils;

import com.artemrogov.streaming.domain.Post;
import com.github.javafaker.Faker;

public class PostEntityGenerator {

    public static Post generate(){
        Faker faker = new Faker();
        Post post = new Post();
        post.setTitle(faker.book().title());
        post.setContent(faker.lorem().paragraph(20));
        post.setActive(faker.bool().bool());
        return post;
    }
}
