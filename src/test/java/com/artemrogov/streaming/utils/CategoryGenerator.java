package com.artemrogov.streaming.utils;

import com.artemrogov.streaming.model.blog.CategoryRequest;
import com.github.javafaker.Faker;

public class CategoryGenerator {
    public static CategoryRequest generateCategory(){
        Faker jDataFaker = new Faker();
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle(jDataFaker.commerce().productName());
        categoryRequest.setActive(jDataFaker.bool().bool());
        categoryRequest.setContent(jDataFaker.lorem().paragraph(2));
        return categoryRequest;
    }
}
