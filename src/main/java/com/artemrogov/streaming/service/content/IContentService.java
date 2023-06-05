package com.artemrogov.streaming.service.content;

import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.dto.datatable.DataTableRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.dto.datatable.PostDataRow;

import java.io.IOException;
import java.util.List;

public interface IContentService {
    void updatePivotalRelations(Long catId, Long postId);
    void removeRelation(Long catId, Long postId);

    void updateRelationsByPostsIds(Long catId, List<Long> postsIds);

    void removeRelationByPostsIds(Long catId, List<Long> postsIds);

    DataTableResultList getPosts(Long limit, Long offset, Long draw);

    void createPosts(List<PostRequest> postRequests);
    PostDataRow create(PostRequest postRequest);
    PostDataRow update(Long id,PostRequest postRequest);

    PostDataRow getPostResponse(Long id);

    void destroy(Long id);

    void destroyByIds(List<Long> ids);


    void generateExcelReport(DataTableRequest request) throws IOException;

}
