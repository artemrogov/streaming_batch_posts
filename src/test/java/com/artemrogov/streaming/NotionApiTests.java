package com.artemrogov.streaming;


import com.artemrogov.streaming.dto.PageNotionResponse;
import com.artemrogov.streaming.service.NotionDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
public class NotionApiTests {

    @Autowired
    private NotionDataService notionDataService;


    @Test
    public void getSimpleQueryDBTest() throws JsonProcessingException {
        String dbId = "896b272824524ef8a2675758fa3e5c96";
        String propertyId = "%7C%5CD%60";
        Mono<PageNotionResponse> dataResponse = notionDataService.getDatabaseValues(dbId,propertyId);
        PageNotionResponse pageNotionResponse = dataResponse.block();

        pageNotionResponse.getResults().forEach(p-> {
            System.out.println(p.getProperties().get("first name").get("rich_text").findValues("plain_text"));
           // System.out.println(p.getProperties().get(1).get("rich_text").findValues("plain_text"));
          //  System.out.println(p.getProperties().get(2).get("rich_text").findValues("plain_text"));
         //   System.out.println(p.getProperties().get(3).get("rich_text").findValues("plain_text"));
        });


    }
}
