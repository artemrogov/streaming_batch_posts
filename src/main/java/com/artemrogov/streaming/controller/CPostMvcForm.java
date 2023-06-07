package com.artemrogov.streaming.controller;


import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.service.content.IContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class CPostMvcForm {

    private final IContentService contentService;


    @GetMapping
    public String getMainPage(){
        return "posts/index";
    }

    @GetMapping(value = "/create")
    public String getCreateForm(Model model){
        model.addAttribute("post", new PostRequest());
        return "posts/create";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditForm(Model model, @PathVariable("id") Long id){
        model.addAttribute("post", this.contentService.getPostResponse(id));
        return "posts/edit";
    }


    @PostMapping(value = "/create")
    public String create(@ModelAttribute PostRequest post, BindingResult errors, Model model){
        if (errors.hasErrors()) {
            return "posts/create";
        }
        this.contentService.create(post);
        return "redirect:/posts";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable("id") Long id, PostRequest post, BindingResult errors, Model model){
        if (errors.hasErrors()) {
            return "posts/edit";
        }
        contentService.update(id,post);
        return "redirect:/posts";
    }
}
