package com.artemrogov.streaming.service;

import com.artemrogov.streaming.dto.DataTableResultList;

import java.util.List;

public interface IContentService {
    void updatePivotalRelations(Long catId, Long postId);
    void removeRelation(Long catId, Long postId);

    void updateRelationsByPostsIds(Long catId, List<Long> postsIds);

    void removeRelationByPostsIds(Long catId, List<Long> postsIds);

    List<DataTableResultList> getPosts(Long limit, Long offset);
}
