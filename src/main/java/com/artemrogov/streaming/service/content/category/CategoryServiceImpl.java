package com.artemrogov.streaming.service.content.category;


import com.artemrogov.streaming.dto.blog.CategoryRequest;
import com.artemrogov.streaming.dto.blog.CategoryResponse;
import com.artemrogov.streaming.domain.Category;
import com.artemrogov.streaming.mapper.CategoryMapper;
import com.artemrogov.streaming.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(@NotNull CategoryRequest categoryRequest) {
        Category category  = this.categoryMapper.convertToEntityFromRequest(categoryRequest);
        return this.categoryMapper.convertToResponse(this.categoryRepository.save(category));
    }

    @Override
    public Category createEntity(@NotNull Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void createAll(@NotNull List<CategoryRequest> categoryRequestList) {
         List<Category> categories = categoryRequestList.stream()
                 .map(categoryMapper::convertToEntityFromRequest)
                 .collect(Collectors.toList());
         this.categoryRepository.saveAll(categories);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getEntity(id);
        categoryMapper.update(request,category);
        return categoryMapper.convertToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getResponse(Long id) {
        return categoryMapper.convertToResponse(getEntity(id));
    }

    @Override
    public Category getEntity(Long id) {
        return this.categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<CategoryResponse> getSimpleListResponseData() {
        return this.categoryRepository.findAll().stream()
                .map(categoryMapper::convertToResponse)
                .collect(Collectors.toList());
    }
}
