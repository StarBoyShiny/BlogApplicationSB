package com.orgPRO.BlogApplication.services;

import com.orgPRO.BlogApplication.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getTags();
    List<Tag> createTags(Set<String> tagNames);
    void deleteTags(UUID id);
    Tag getTagById(UUID id);
    List<Tag> getTagByids(Set<UUID> ids);
}
