package com.artemrogov.streaming.finder;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FinderProducerData {
    private final StreamBridge streamBridge;

    public boolean publish(String msg){
        return streamBridge.send("finder-out-0",msg);
    }
}
