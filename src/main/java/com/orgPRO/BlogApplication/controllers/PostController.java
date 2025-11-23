package com.orgPRO.BlogApplication.controllers;

import com.orgPRO.BlogApplication.domain.CreatePostRequest;
import com.orgPRO.BlogApplication.domain.UpdatePostRequest;
import com.orgPRO.BlogApplication.domain.dtos.CreatePostRequestDto;
import com.orgPRO.BlogApplication.domain.dtos.PostDto;
import com.orgPRO.BlogApplication.domain.dtos.UpdatePostRequestDto;
import com.orgPRO.BlogApplication.domain.entities.Post;
import com.orgPRO.BlogApplication.domain.entities.User;
import com.orgPRO.BlogApplication.mapper.PostMapper;
import com.orgPRO.BlogApplication.services.UserService;
import com.orgPRO.BlogApplication.services.impl.PostServiceImpl;
import com.orgPRO.BlogApplication.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> list = posts.stream()
                .map(postMapper::toPostDto)
                .toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDarftPosts(loggedInUser);

        List<PostDto> draftPostDtos = draftPosts.stream()
                .map(postMapper::toPostDto)
                .toList();
        return new ResponseEntity<>(draftPostDtos, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequestDto requestDto, @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(requestDto);
        Post post = postService.createPost(loggedInUser, createPostRequest);
        PostDto postDto = postMapper.toPostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto requestDto
    ) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(requestDto);
        Post post = postService.updatePost(id, updatePostRequest);
        PostDto postDto = postMapper.toPostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toPostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
