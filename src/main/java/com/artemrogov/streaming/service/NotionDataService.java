package com.artemrogov.streaming.service;


import com.artemrogov.streaming.dto.PageNotionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotionDataService {

    private final WebClient webClient;

    private final static String API_DATABASE_URL = "databases/";

    public Mono<PageNotionResponse> getDatabaseValues(String idDatabase, String properties){
        return webClient.post().uri(uriBuilder ->
            uriBuilder.path(API_DATABASE_URL+"/{idDatabase}/query")
                    .queryParam("filter_properties","{properties}")
                    .build(idDatabase,properties))
                .retrieve()
                .bodyToMono(PageNotionResponse.class);
    }
}
