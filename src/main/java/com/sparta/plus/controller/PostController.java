package com.sparta.plus.controller;

import com.sparta.plus.dto.ApiResponseDto;
import com.sparta.plus.dto.PostRequestDto;
import com.sparta.plus.dto.PostResponseDto;
import com.sparta.plus.security.UserDetailsImpl;
import com.sparta.plus.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 전체 게시글 목록 조회 API
    // 제목, 작성자명(nickname), 작성 날짜를 조회하기
    // 작성 날짜 기준으로 내림차순 정렬하기
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPost();
    }

    // 게시글 작성 API
    // 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    // 제목, 작성 내용을 입력하기
    @PostMapping("/posts")
    public ResponseEntity<ApiResponseDto> createdPost(@RequestBody PostRequestDto postRequestDto, @RequestBody UserDetailsImpl userDetails) {
        postService.createdPost(postRequestDto, userDetails);
        return ResponseEntity.status(200).body(new ApiResponseDto("게시글 작성이 되었습니다.", HttpStatus.OK.value()));
    }
    // 게시글 조회 API
    // 제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기 (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    @GetMapping("/posts/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }
    // 게시글 수정 API
    // 토큰을 검사하여, 해당 사용자가 작성한 게시글만 수정 가능
    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @RequestBody UserDetailsImpl userDetails) {
        postService.updatePost(id, postRequestDto, userDetails);
        return ResponseEntity.status(200).body(new ApiResponseDto("게시글 수정에 성공했습니다", HttpStatus.OK.value()));
    }
    // 게시글 삭제 API
    // 토큰을 검사하여, 해당 사용자가 작성한 게시글만 삭제 가능
    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @RequestBody UserDetailsImpl userDetails) {
        return postService.deletePost(id, postRequestDto, userDetails);
    }
}