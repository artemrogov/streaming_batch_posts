package com.artemrogov.streaming.config.httpclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestOldTemplateClient {
    @Bean
    public RestTemplate restClientTemplate(){
        return new RestTemplate();
    }
}
