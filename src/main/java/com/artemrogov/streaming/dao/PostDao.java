package com.artemrogov.streaming.dao;

import com.artemrogov.streaming.domain.Post;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface PostDao extends CrudOperationsDao<Post,Long> {
}
