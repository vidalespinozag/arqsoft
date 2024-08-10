package com.acme.banking.platform.students.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record RegisterClientResource (
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id,
    String firstName,
    String lastName,
    String dni,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime createdAt
) {
    public RegisterClientResource withId(Long id) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new RegisterClientResource(id, this.firstName, this.lastName, this.dni, createdAt);
    }
}
