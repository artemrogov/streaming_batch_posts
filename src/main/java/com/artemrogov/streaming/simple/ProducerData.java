package com.artemrogov.streaming.simple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerData {
    private final StreamBridge streamBridge;

    public boolean publishMessage(String msg){
        return streamBridge.send("messagesSenderChannel-out-0",msg);
    }
}
