package com.artemrogov.streaming;

import com.artemrogov.streaming.entities.Category;
import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.repositories.CategoryRepository;
import com.artemrogov.streaming.repositories.PostDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StreamingApplicationTests {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PostDataRepository postDataRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCategoriesRelationsPosts(){
		Post post = new Post();
		post.setTitle("test post 1");
		post.setContent("test content post 1");
		post.setActive(true);

		Category category = new Category();
		category.setTitle("cars");
		category.setActive(true);
		category.setContent("test cars");

		post.getCategories().add(category);
		category.getPosts().add(post);

		categoryRepository.save(category);

	}


	@Test
	public void testSavedPost(){
		Post nPost = new Post();
		nPost.setTitle("post 3");
		nPost.setContent("post 3");
		nPost.setActive(true);
		postDataRepository.save(nPost);
	}
}
