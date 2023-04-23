package com.artemrogov.streaming.finder;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class FinderConsumerData {
    @Bean(name = "finderPublish")
    public Consumer<Message<String>> finderPublish(){
        return msg->{
            log.info("Finder publish data: " + msg.getPayload());
        };
    }
}
