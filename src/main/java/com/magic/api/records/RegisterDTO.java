package com.magic.api.records;

public record RegisterDTO(
        String username,
        String password,
        String email
) {
}
