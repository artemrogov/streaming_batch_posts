package com.artemrogov.streaming.mapper;


import com.artemrogov.streaming.model.datatable.PostDataRow;
import com.artemrogov.streaming.model.blog.PostRequest;
import com.artemrogov.streaming.domain.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    public abstract Post convertToPost(PostRequest postRequest);
    public abstract PostDataRow convertPostResponse(Post post);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateDataPost(PostRequest postRequest, @MappingTarget Post post);
}
