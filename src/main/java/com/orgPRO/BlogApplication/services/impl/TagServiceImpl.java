package com.orgPRO.BlogApplication.services.impl;

import com.orgPRO.BlogApplication.domain.entities.Tag;
import com.orgPRO.BlogApplication.repositories.TagRepository;
import com.orgPRO.BlogApplication.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags(){
        return tagRepository.findAllwithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        //found existing tag entities
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        //found names of tags here
        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        //filtering names of unique tags that needs to be created leaving already existing ones
        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        //to return new and already tags in one list
        List<Tag> savedTags = new ArrayList<>();

        if (!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        return savedTags;

    }

    @Transactional
    @Override
    public void deleteTags(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if (!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No tag found with id: " + id));
    }

    @Override
    public List<Tag> getTagByids(Set<UUID> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);

        if (foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("Some tag ids doesnt exist");

        }
        return foundTags;
    }
}
