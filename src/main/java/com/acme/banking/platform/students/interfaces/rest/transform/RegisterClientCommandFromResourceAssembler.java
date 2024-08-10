package com.acme.banking.platform.students.interfaces.rest.transform;

import com.acme.banking.platform.students.domain.commands.RegisterClient;
import com.acme.banking.platform.students.interfaces.rest.resources.RegisterClientResource;

public class RegisterClientCommandFromResourceAssembler {
    public static RegisterClient toCommandFromResource(RegisterClientResource resource) {
        return new RegisterClient(
                resource.id(),
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.createdAt());
    }
}
