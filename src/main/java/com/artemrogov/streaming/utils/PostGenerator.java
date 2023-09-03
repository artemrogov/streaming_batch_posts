package com.artemrogov.streaming.utils;

import com.artemrogov.streaming.model.blog.PostRequest;
import com.github.javafaker.Faker;

public class PostGenerator {
    public static PostRequest generatePosts(){
        Faker jDataFaker = new Faker();
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle(jDataFaker.commerce().productName());
        postRequest.setActive(jDataFaker.bool().bool());
        postRequest.setContent(jDataFaker.lorem().paragraph(5));
        return postRequest;
    }
}
