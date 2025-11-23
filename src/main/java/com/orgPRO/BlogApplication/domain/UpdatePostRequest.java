package com.orgPRO.BlogApplication.domain;



import com.orgPRO.BlogApplication.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {
    private UUID id;

    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private UUID categoryId;

    @Builder.Default
    private Set<UUID> tagIds= new HashSet<>();

    private PostStatus status;
}
