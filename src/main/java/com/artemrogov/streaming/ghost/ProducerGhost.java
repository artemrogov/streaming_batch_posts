package com.artemrogov.streaming.ghost;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerGhost {
    private final StreamBridge streamBridge;

    public boolean publishGhostMessage(String msg){
        return streamBridge.send("ghost-out-0",msg);
    }
}
