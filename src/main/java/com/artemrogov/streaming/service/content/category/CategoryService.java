package com.artemrogov.streaming.service.content.category;

import com.artemrogov.streaming.dto.blog.CategoryRequest;
import com.artemrogov.streaming.dto.blog.CategoryResponse;
import com.artemrogov.streaming.domain.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest categoryRequest);
    Category createEntity(Category category);
    void createAll(List<CategoryRequest> categoryRequestList);

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse getResponse(Long id);

    Category getEntity(Long id);

    List<CategoryResponse> getSimpleListResponseData();
}
