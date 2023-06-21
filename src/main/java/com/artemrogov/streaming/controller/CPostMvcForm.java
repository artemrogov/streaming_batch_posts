package com.artemrogov.streaming.controller;


import com.artemrogov.streaming.dto.blog.CategoryResponse;
import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.service.content.IContentService;
import com.artemrogov.streaming.service.content.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class CPostMvcForm {
    private final IContentService contentService;
    private final CategoryService categoryService;
    private final OAuth2AuthorizedClientService authService;

    @GetMapping
    public String getIndexPage(Model model, OAuth2AuthenticationToken auth){
        model.addAttribute("jwtToken",getJwtTokenAuthUser(auth));
        model.addAttribute("refreshToken",getRefreshToken(auth));
        return "posts/index";
    }

    @GetMapping(value = "/create")
    public String getCreateForm(Model model){

        List<CategoryResponse> response = categoryService.getSimpleListResponseData();
        model.addAttribute("categories",response);

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

    private OAuth2AuthorizedClient getClient(OAuth2AuthenticationToken authentication){
        return  authService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );
    }

    private String getJwtTokenAuthUser(OAuth2AuthenticationToken authentication){
        return this.getClient(authentication).getAccessToken().getTokenValue();
    }

    private String getRefreshToken(OAuth2AuthenticationToken authentication){
        return Objects.requireNonNull(this.getClient(authentication).getRefreshToken()).getTokenValue();
    }
}
