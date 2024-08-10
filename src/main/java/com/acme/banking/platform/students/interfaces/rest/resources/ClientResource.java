package com.acme.banking.platform.students.interfaces.rest.resources;

public record ClientResource (
    Long id,
    String firstName,
    String lastName,
    String dni
) {}
