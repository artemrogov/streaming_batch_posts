package com.artemrogov.streaming.controller;


import com.artemrogov.streaming.dto.datatable.DataTableRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.dto.datatable.DeleteListRequest;
import com.artemrogov.streaming.service.content.IContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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


    @DeleteMapping(value = "/destroy/{id}")
    public ResponseEntity<String> destroyPosts(@PathVariable("id") Long id){
        contentService.destroy(id);
        return ResponseEntity.ok("delete posts");
    }


    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllPosts(@RequestBody DeleteListRequest request){
        contentService.destroyByIds(request.getIds());
        return ResponseEntity.ok("delete all posts");
    }

    @PostMapping(value = "/generate-report")
    public ResponseEntity<Resource> generateReport(@RequestBody DataTableRequest request){
        try {
            String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            String pathFile = contentService.generateExcelReport(request);
            Path path = Paths.get(pathFile);
            Resource resource = null;

            try {
                resource = new UrlResource(path.toUri());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            assert resource != null;
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
