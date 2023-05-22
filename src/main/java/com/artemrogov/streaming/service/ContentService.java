package com.artemrogov.streaming.service;


import com.artemrogov.streaming.entities.Category;
import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.repositories.CategoryRepository;
import com.artemrogov.streaming.repositories.PostDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService implements IContentService{

    private final PostDataRepository postDataRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public void updateRelationsByPostsIds(Long catId, List<Long> postsIds){
        if (postsIds.isEmpty()){
            return;
        }
        postsIds.forEach(id-> updatePivotalRelations(catId,id));
    }


    @Override
    public void removeRelationByPostsIds(Long catId, List<Long> postsIds){
        if (postsIds.isEmpty()){
            return;
        }
        postsIds.forEach(id-> removeRelation(catId,id));
    }

    @Override
    public void updatePivotalRelations(Long catId, Long postId){
        Category catCurrent = this.categoryRepository.findById(catId).orElseThrow();
        Post cPost = this.postDataRepository.findById(postId).orElseThrow();
        catCurrent.getPosts().add(cPost);
        cPost.getCategories().add(catCurrent);
        categoryRepository.save(catCurrent);
    }


    @Override
    public void removeRelation(Long catId, Long postId){
        Category catCurrent = this.categoryRepository.findById(catId).orElseThrow();
        Post cPost = this.postDataRepository.findById(postId).orElseThrow();
        catCurrent.getPosts().remove(cPost);
        cPost.getCategories().remove(catCurrent);
        categoryRepository.save(catCurrent);
    }
}
