package com.artemrogov.streaming;


import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.service.content.ContentService;
import com.artemrogov.streaming.utils.PostGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class RelationUpdatedTests {


    @Autowired
    private ContentService contentService;

    @Test
    public void testUpdateRelations(){
        contentService.updatePivotalRelations(1000L,1005L);
    }

    @Test
    public void testRemoveRelations(){
        contentService.removeRelation(1000L,1000L);
    }



    @Test
    public void testCreateNewPostAddInExistsCat(){
        contentService.updatePivotalRelations(1000L,1006L);
        contentService.updatePivotalRelations(1000L,1000L);
    }


    @Test
    public void testManyLinksExistsPosts(){
        contentService.updatePivotalRelations(1001L,1006L);
    }


    @Test
    public void fillPosts(){
          List<PostRequest> postsRequestsData = Stream.generate(PostGenerator::generatePosts)
                  .limit(20).collect(Collectors.toList());
         this.contentService.createPosts(postsRequestsData);
    }

    @Test
    public void testImagesFillPost(){

    }
}
