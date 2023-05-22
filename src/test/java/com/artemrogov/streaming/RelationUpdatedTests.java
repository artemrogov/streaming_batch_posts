package com.artemrogov.streaming;


import com.artemrogov.streaming.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
