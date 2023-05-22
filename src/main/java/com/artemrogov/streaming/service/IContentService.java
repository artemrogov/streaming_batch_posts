package com.artemrogov.streaming.service;

import java.util.List;

public interface IContentService {
    void updatePivotalRelations(Long catId, Long postId);
    void removeRelation(Long catId, Long postId);

    void updateRelationsByPostsIds(Long catId, List<Long> postsIds);

    void removeRelationByPostsIds(Long catId, List<Long> postsIds);
}
