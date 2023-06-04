package com.artemrogov.streaming.service.content;


import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.dto.datatable.PostDataRow;
import com.artemrogov.streaming.entities.Category;
import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.mapper.BlogMapper;
import com.artemrogov.streaming.repositories.CategoryRepository;
import com.artemrogov.streaming.repositories.PostDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService implements IContentService{
    private final PostDataRepository postDataRepository;
    private final CategoryRepository categoryRepository;
    private final BlogMapper blogMapper;


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


    @Override
    public DataTableResultList getPosts(Long limit, Long offset, Long draw) {

        List<PostDataRow> dataRows = postDataRepository.findPostsLimiterOffset(limit, offset)
                .stream().map(blogMapper::convertPostResponse)
                .collect(Collectors.toList());

         Long countPosts = this.postDataRepository.countPosts();

         return DataTableResultList.builder()
                 .data(dataRows)
                 .draw(draw)
                 .recordsTotal(countPosts)
                 .recordsFiltered(countPosts)
                 .build();
    }

    @Override
    public void createPosts(List<PostRequest> postRequests) {
         List<Post> postData = postRequests.stream().map(blogMapper::convertToPost).collect(Collectors.toList());
         this.postDataRepository.saveAll(postData);
    }

    @Override
    public PostDataRow create(PostRequest postRequest) {
        Post postCreated = this.postDataRepository.save(this.blogMapper.convertToPost(postRequest));
        return blogMapper.convertPostResponse(postCreated);
    }

    @Override
    public PostDataRow update(Long id, PostRequest postRequest) {
        Post post = this.postDataRepository.findById(id).orElseThrow();
        this.blogMapper.updateDataPost(postRequest,post);
        Post postUpdated = this.postDataRepository.save(post);
        return blogMapper.convertPostResponse(postUpdated);
    }

    @Override
    public PostDataRow getPostResponse(Long id) {
        return this.blogMapper.convertPostResponse(this.postDataRepository.findById(id).orElseThrow());
    }

    @Override
    public void destroy(Long id) {
        this.postDataRepository.deleteById(id);
    }

    @Override
    public void destroyByIds(List<Long> ids) {
        this.postDataRepository.deleteAllById(ids);
    }
}
