package com.acme.banking.platform.students.interfaces.rest.transform;

import com.acme.banking.platform.students.domain.commands.EditClient;
import com.acme.banking.platform.students.interfaces.rest.resources.EditClientResource;

public class EditClientCommandFromResourceAssembler {
    public static EditClient toCommandFromResource(EditClientResource resource) {
        return new EditClient(
            resource.id(),
            resource.firstName(),
            resource.lastName(),
            resource.dni(),
            resource.updatedAt());
    }
}
