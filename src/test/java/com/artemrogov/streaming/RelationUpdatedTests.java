package com.artemrogov.streaming;


import com.artemrogov.streaming.service.AsscociateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelationUpdatedTests {


    @Autowired
    private AsscociateService asscociateService;

    @Test
    public void testUpdateRelations(){
        asscociateService.updatePivotalRelations(1000L,1005L);
    }

    @Test
    public void testRemoveRelations(){
        asscociateService.removeRelation(1000L,1000L);
    }



    @Test
    public void testCreateNewPostAddInExistsCat(){
        asscociateService.updatePivotalRelations(1000L,1006L);
        asscociateService.updatePivotalRelations(1000L,1000L);
    }

}
