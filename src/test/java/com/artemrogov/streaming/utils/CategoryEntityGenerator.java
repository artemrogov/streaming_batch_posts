package com.artemrogov.streaming.utils;

import com.artemrogov.streaming.domain.Category;
import com.github.javafaker.Faker;

public class CategoryEntityGenerator {

    public static Category generate(){
        Faker faker = new Faker();
        Category category = new Category();
        category.setTitle(faker.book().publisher());
        category.setContent(faker.lorem().paragraph(20));
        category.setActive(faker.bool().bool());
        return category;
    }
}
