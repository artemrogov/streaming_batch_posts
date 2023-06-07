package com.artemrogov.streaming.controller;

import com.artemrogov.streaming.finder.FinderProducerData;
import com.artemrogov.streaming.ghost.ProducerGhost;
import com.artemrogov.streaming.simple.ProducerData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api-stream")
@RequiredArgsConstructor
public class CMain {

    private final ProducerData producerData;
    private final FinderProducerData finderProducerData;

    private final ProducerGhost producerGhost;

    @PostMapping(value = "/send01")
    public boolean send01(){
        return producerData.publishMessage("Test 01 channel");
    }


    @PostMapping(value = "/send02")
    public boolean send02(){
        return finderProducerData.publish("My message finder data");
    }


    @PostMapping(value = "/send03")
    public boolean send03(){
        return producerGhost.publishGhostMessage("MY GHOST DATA PUBLISH!!!!");
    }
}
