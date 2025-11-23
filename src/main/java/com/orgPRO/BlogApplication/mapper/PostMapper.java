package com.orgPRO.BlogApplication.mapper;

import com.orgPRO.BlogApplication.domain.CreatePostRequest;
import com.orgPRO.BlogApplication.domain.UpdatePostRequest;
import com.orgPRO.BlogApplication.domain.dtos.CreatePostRequestDto;
import com.orgPRO.BlogApplication.domain.dtos.PostDto;
import com.orgPRO.BlogApplication.domain.dtos.UpdatePostRequestDto;
import com.orgPRO.BlogApplication.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toPostDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto requestDto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto requestDto);
}
