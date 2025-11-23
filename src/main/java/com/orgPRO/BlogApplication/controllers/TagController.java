package com.orgPRO.BlogApplication.controllers;

import com.orgPRO.BlogApplication.domain.dtos.CreateTagsRequest;
import com.orgPRO.BlogApplication.domain.dtos.TagDto;
import com.orgPRO.BlogApplication.domain.entities.Tag;
import com.orgPRO.BlogApplication.mapper.TagMapper;
import com.orgPRO.BlogApplication.services.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getTags() {
        List<Tag> tags = tagService.getTags();
        List<TagDto> tagRespons = tags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(tagRespons, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
        List<Tag> newTags = tagService.createTags(createTagsRequest.getNames());
        List<TagDto> newTagsResponses = newTags.stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return new ResponseEntity<>(newTagsResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTags(@PathVariable UUID id){
        tagService.deleteTags(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
