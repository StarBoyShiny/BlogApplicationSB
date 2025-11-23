package com.orgPRO.BlogApplication.services;

import com.orgPRO.BlogApplication.domain.CreatePostRequest;
import com.orgPRO.BlogApplication.domain.UpdatePostRequest;
import com.orgPRO.BlogApplication.domain.entities.Post;
import com.orgPRO.BlogApplication.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDarftPosts(User user);
    Post createPost(User user, CreatePostRequest request);
    Post updatePost(UUID id,UpdatePostRequest updatePostRequest);

    Post getPost(UUID id);

    void deletePost(UUID id);
}
