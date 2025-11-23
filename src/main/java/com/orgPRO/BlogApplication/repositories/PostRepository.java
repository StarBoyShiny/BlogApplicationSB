package com.orgPRO.BlogApplication.repositories;

import com.orgPRO.BlogApplication.domain.PostStatus;
import com.orgPRO.BlogApplication.domain.entities.Category;
import com.orgPRO.BlogApplication.domain.entities.Post;
import com.orgPRO.BlogApplication.domain.entities.Tag;
import com.orgPRO.BlogApplication.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findallByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
