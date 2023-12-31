package com.sparta.plus.repository;

import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPostByModifiedAtDesc();
    // 전체 게시물 내림차순으로 정렬

}
