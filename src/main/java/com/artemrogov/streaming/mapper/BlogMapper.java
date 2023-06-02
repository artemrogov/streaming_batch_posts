package com.artemrogov.streaming.mapper;


import com.artemrogov.streaming.dto.PostDataRow;
import com.artemrogov.streaming.dto.PostRequest;
import com.artemrogov.streaming.entities.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {
    public abstract Post convertToPost(PostRequest postRequest);
    public abstract PostDataRow convertPostResponse(Post post);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateDataPost(PostRequest postRequest, @MappingTarget Post post);
}
