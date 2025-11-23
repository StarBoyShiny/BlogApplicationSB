package com.orgPRO.BlogApplication.mapper;

import com.orgPRO.BlogApplication.domain.PostStatus;
import com.orgPRO.BlogApplication.domain.dtos.TagDto;
import com.orgPRO.BlogApplication.domain.entities.Post;
import com.orgPRO.BlogApplication.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null) {
            return 0;
        }

        long count = posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
        return (int) count;

    }
}
