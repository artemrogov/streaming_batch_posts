package com.artemrogov.streaming;


import com.artemrogov.streaming.dao.ContentBlogDao;
import com.artemrogov.streaming.dto.PageNotionResponse;
import com.artemrogov.streaming.dto.TableModel;
import com.artemrogov.streaming.service.NotionDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@Slf4j
public class NotionApiTests {

    @Autowired
    private NotionDataService notionDataService;


    @Autowired
    private ContentBlogDao contentBlogDao;


    @Test
    public void getSimpleQueryDBTest() throws JsonProcessingException {
        String dbId = "896b272824524ef8a2675758fa3e5c96";

        MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();

        queries.add("filter_properties","%7C%5CD%60");
        queries.add("filter_properties","%3Bh_o");
        queries.add("filter_properties","ZC%40Y");

        Mono<PageNotionResponse> dataResponse = notionDataService.getDatabaseValues(dbId,queries);
        PageNotionResponse pageNotionResponse = dataResponse.block();
        List<TableModel> tablePerson = new ArrayList<>();
        assert pageNotionResponse != null;

        pageNotionResponse.getResults().forEach(s->{
            TableModel model = new TableModel();
            model.setFirstName(String.valueOf(s.getProperties().get("first name").get("rich_text").findValues("plain_text")));
            model.setLastName(s.getProperties().get("last name").get("rich_text").findValues("plain_text").toString());
            model.setContent(s.getProperties().get("content").get("rich_text").findValues("plain_text").toString());
            tablePerson.add(model);
        });


        for (TableModel tableModel : tablePerson){
            System.out.println(tableModel.getFirstName() + " :" + tableModel.getLastName());
        }
    }


    @Test
    public void testDaoSimple(){

        contentBlogDao.getData().forEach(p->{
            System.out.println(p.getTitle() + " ids: " + p.getCategoriesIds());
        });

    }
}
