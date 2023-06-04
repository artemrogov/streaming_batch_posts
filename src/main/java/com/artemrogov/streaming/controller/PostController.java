package com.artemrogov.streaming.controller;


import com.artemrogov.streaming.dto.datatable.DataTableRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.service.content.IContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final IContentService contentService;

    @PostMapping
    public ResponseEntity<DataTableResultList> getPosts(@RequestBody DataTableRequest request){
        return  ResponseEntity.ok(contentService.getPosts(request.getLength(),request.getStart(), request.getDraw()));
    }
}
