package com.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record RentalResponse(
    Long id,
    String name,
    Integer surface,
    Integer price,
    String picture,
    String description,
    @JsonProperty("owner_id") Long ownerId,
    @JsonProperty("created_at") LocalDateTime createdAt,
    @JsonProperty("updated_at") LocalDateTime updatedAt
) {}
