package com.artemrogov.streaming.mapper;

import com.artemrogov.streaming.dto.blog.CategoryRequest;
import com.artemrogov.streaming.dto.blog.CategoryResponse;
import com.artemrogov.streaming.entities.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public abstract Category convertToEntityFromRequest(CategoryRequest categoryRequest);
    public abstract CategoryResponse convertToResponse(Category category);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(CategoryRequest categoryRequest, @MappingTarget Category category);
}
