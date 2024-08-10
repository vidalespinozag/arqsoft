package com.acme.banking.platform.students.interfaces.rest.resources;

import com.acme.banking.platform.students.domain.projections.Client;
import com.acme.banking.platform.shared.domain.model.valueobjects.Error;
import java.util.List;

public record ListClientResponseResource(
    List<Client> success,
    List<Error> errors
) {}
