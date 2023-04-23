package com.artemrogov.streaming.repositories;

import com.artemrogov.streaming.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDataRepository extends JpaRepository<Post,Long> { }
