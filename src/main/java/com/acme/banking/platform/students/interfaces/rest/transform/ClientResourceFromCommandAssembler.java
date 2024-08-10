package com.acme.banking.platform.students.interfaces.rest.transform;

import com.acme.banking.platform.students.domain.commands.EditClient;
import com.acme.banking.platform.students.domain.commands.RegisterClient;
import com.acme.banking.platform.students.interfaces.rest.resources.ClientResource;

public class ClientResourceFromCommandAssembler {
    public static ClientResource toResourceFromRegisterClient(RegisterClient command) {
        return new ClientResource(
            command.getId(),
            command.getFirstName(),
            command.getLastName(),
            command.getDni());
    }

    public static ClientResource toResourceFromEditClient(EditClient command) {
        return new ClientResource(
                command.getId(),
                command.getFirstName(),
                command.getLastName(),
                command.getDni());
    }
}
