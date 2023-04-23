package com.artemrogov.streaming.ghost;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class ConsumerGhost {
    @Bean(name = "ghostPublish")
    public Consumer<Message<String>> listenData(){
        return msg->{
            log.info("TEST LISTEN GHOST message: " + msg.getPayload());
        };
    }
}
