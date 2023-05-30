package com.artemrogov.streaming;


import com.artemrogov.streaming.dto.PostResponse;
import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.repositories.PostDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MvcMainController {


    private final PostDataRepository postDataRepository;


    @GetMapping(value = "/test-page01")
    public String getMainPage(){
        return "index";
    }


    @GetMapping(value = "/test-page02")
    public String getPage02(Model model){

        List<String> simpleTestValues = new ArrayList<>();

        simpleTestValues.add("item menu 01");
        simpleTestValues.add("item menu 02");
        simpleTestValues.add("item menu 03");
        simpleTestValues.add("item menu 04");
        simpleTestValues.add("item menu 05");

        model.addAttribute("items",simpleTestValues);

        return "page02";
    }


    @GetMapping(value = "/posts-list")
    public String getPage03(Model model){
        model.addAttribute("posts",getPostsData());
        return "page03";
    }


    @GetMapping(value = "/test-page04")
    public String getPage04(Model model){
        model.addAttribute("post", new Post());
        return "simple_form";
    }



    @GetMapping(value = "/edit-post/{id}")
    public String getPage05(Model model, @PathVariable("id") Long id){
        model.addAttribute("post", postDataRepository.findById(id).orElseThrow());
        return "simple_form2";
    }


    @PostMapping(value = "/savePost")
    public String savePost(@ModelAttribute Post post, BindingResult errors, Model model){

        if (errors.hasErrors()) {
            return "simple_form";
        }

        postDataRepository.save(post);

        return "redirect:/posts-list";
    }


    @PostMapping(value = "/updatePost/{id}")
    public String updatePost(@PathVariable("id") Long id, Post post, BindingResult errors, Model model){

        if (errors.hasErrors()) {
            post.setId(id);
            return "simple_form2";
        }

        postDataRepository.save(post);

        return "redirect:/posts-list";
    }


    private List<PostResponse> getPostsData(){
        return postDataRepository.findAll().stream()
                .map(p-> PostResponse.builder().id(p.getId())
                        .title(p.getTitle()).content(p.getContent()).build())
                .collect(Collectors.toList());
    }
}
