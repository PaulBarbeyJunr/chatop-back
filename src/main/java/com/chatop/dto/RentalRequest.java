package com.chatop.dto;

public record RentalRequest(
    String name,
    Integer surface,
    Integer price,
    String picture,
    String description
) {}
