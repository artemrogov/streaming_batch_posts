package com.artemrogov.streaming.repositories;

import com.artemrogov.streaming.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PostDataRepository extends JpaRepository<Post,Long> {

    @Query(value = "SELECT * FROM posts ORDER BY id LIMIT :limitParam OFFSET :offsetParam",nativeQuery = true)
    List<Post>findPostsLimiterOffset(@Param("limitParam") Long limit, @Param("offsetParam") Long offset);


    @Query(value = "SELECT * FROM posts WHERE start_date >=:startDate AND end_date<=:endDate", nativeQuery = true)
    List<Post>findPostsByDateRange(@Param("startDate") Instant start, @Param("endDate") Instant end);


    @Query(value = "select count(*) FROM posts",nativeQuery = true)
    Long countPosts();
}
