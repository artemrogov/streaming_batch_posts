package com.artemrogov.streaming.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping(value = "/main")
    public String getPublicPage(){
        return "index";
    }

}
