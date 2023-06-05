package com.artemrogov.streaming.controller;


import com.artemrogov.streaming.dto.datatable.DataTableRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.service.content.IContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final IContentService contentService;

    @PostMapping
    public ResponseEntity<DataTableResultList> getPosts(@RequestBody DataTableRequest request){
        return  ResponseEntity.ok(contentService.getPosts(request.getLength(),request.getStart(), request.getDraw()));
    }


    @PostMapping(value = "/generate-report")
    public void generateReport(@RequestBody DataTableRequest request){
        try {
            log.info("TEST REQUEST:" + request.toString());
            contentService.generateExcelReport(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
