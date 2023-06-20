package com.artemrogov.streaming.config.httpclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfig {

    @Value(value = "${art.notion.api.key}")
    private String apiKey;

    @Value(value = "${art.notion.api.url}")
    private String urlApi;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(urlApi);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return webClientBuilder
                .baseUrl(urlApi)
                .uriBuilderFactory(factory)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.setBearerAuth(apiKey);
                    httpHeaders.set("Notion-Version","2022-06-28");
                }).build();
    }
}
