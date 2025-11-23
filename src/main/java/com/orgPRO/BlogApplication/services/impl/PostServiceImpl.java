package com.orgPRO.BlogApplication.services.impl;

import com.orgPRO.BlogApplication.domain.CreatePostRequest;
import com.orgPRO.BlogApplication.domain.PostStatus;
import com.orgPRO.BlogApplication.domain.UpdatePostRequest;
import com.orgPRO.BlogApplication.domain.dtos.PostDto;
import com.orgPRO.BlogApplication.domain.entities.Category;
import com.orgPRO.BlogApplication.domain.entities.Post;
import com.orgPRO.BlogApplication.domain.entities.Tag;
import com.orgPRO.BlogApplication.domain.entities.User;
import com.orgPRO.BlogApplication.repositories.PostRepository;
import com.orgPRO.BlogApplication.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryServiceImpl categoryService;
    private final TagServiceImpl tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Transactional(readOnly = true)
    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category categoryById = categoryService.getCategoryById(categoryId);
            Tag tagById = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    categoryById,
                    tagById
            );
        }

        if (categoryId != null) {
            Category categoryById = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    categoryById
            );
        }

        if (tagId != null) {
            Tag tagById = tagService.getTagById(tagId);
            return postRepository.findallByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tagById
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDarftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest request) {
        Post newPost = new Post();
        newPost.setTitle(request.getTitle());
        newPost.setContent(request.getContent());
        newPost.setStatus(request.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(request.getContent()));

        Category category = categoryService.getCategoryById(request.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = request.getTagIds();
        List<Tag> tags = tagService.getTagByids(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Post doesnt exist with this id " + id));
        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

        UUID categoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(categoryId)) {
            Category newCategory = categoryService.getCategoryById(categoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> tagByids = tagService.getTagByids(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(tagByids));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public Post getPost(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post doesnt exist with this id " + id));
        return post;
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }


    public Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);

    }
}
