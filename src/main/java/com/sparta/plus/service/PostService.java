package com.sparta.plus.service;

import com.sparta.plus.dto.ApiResponseDto;
import com.sparta.plus.dto.PostRequestDto;
import com.sparta.plus.dto.PostResponseDto;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;
import com.sparta.plus.entity.UserRoleEnum;
import com.sparta.plus.exception.TokenValidationException;
import com.sparta.plus.jwt.JwtUtil;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final User user;

    // 전체 게시글 목록 조회 API
    // 제목, 작성자명(nickname), 작성 날짜를 조회하기
    // 작성 날짜 기준으로 내림차순 정렬하기
    public List<PostResponseDto> getPost() {
        return postRepository.findAllByPostByModifiedAtDesc().stream()
                .map(PostResponseDto::new).toList();
    }

    // 게시글 작성 API
    // 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    // 제목, 작성 내용을 입력하기
    @Transactional
    public PostResponseDto createdPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nickName = authentication.getName();

        String token = jwtUtil.getJwtFromHeader((HttpServletRequest) postRequestDto);
        if (!jwtUtil.validateToken(token)) {
            throw new TokenValidationException("유효하지 않은 토큰입니다.");
        }
        User user = userDetails.getUser();
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContents(), user);
        return new PostResponseDto(postRepository.save(post));
    }


    // 게시글 조회 API
    // 제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기 (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    public PostResponseDto getPostById(Long id) {
        Post post = findByPost(id);
        return new PostResponseDto(post);
    }

    private Post findByPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
    }

    // 게시글 수정 API
    // 토큰을 검사하여, 해당 사용자가 작성한 게시글만 수정 가능

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        String token = jwtUtil.getJwtFromHeader((HttpServletRequest) postRequestDto);
        if (!jwtUtil.validateToken(token)) {
            throw new TokenValidationException("유효하지 않은 토큰입니다.");
        }
        // 본인 작성글만 수정 가능
        User currentUser = userDetails.getUser();
        if (!user.getRole().equals(UserRoleEnum.ADMIN)&&!post.getAuthor().equals(user.getNickName())) {
            throw new IllegalArgumentException("작성자 또는 관리자만 게시글을 수정할 수 있습니다.");
        }
        post.setTitle(postRequestDto.getTitle());
        post.setContents(postRequestDto.getContents());

        return new PostResponseDto(post);
    }

    // 게시글 삭제 API
    // 토큰을 검사하여, 해당 사용자가 작성한 게시글만 삭제 가능
    @Transactional
    public PostResponseDto  deletePost(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = postRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        String token = jwtUtil.getJwtFromHeader((HttpServletRequest) postRequestDto);
        if (!jwtUtil.validateToken(token)) {
            throw new TokenValidationException("유효하지 않은 토큰입니다.");
        }

        if (post != null) {
            // 해당 사용자가 작성한 게시글인지 또는 관리자인지 확인
            User currentUser = userDetails.getUser();
            if (!user.getRole().equals(UserRoleEnum.ADMIN) && !post.getAuthor().equals(user.getNickName())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자 또는 관리자만 게시글을 삭제할 수 있습니다.");
            } else {
                postRepository.delete(post);
            }
        }

        return new PostResponseDto(post);
    }
}
