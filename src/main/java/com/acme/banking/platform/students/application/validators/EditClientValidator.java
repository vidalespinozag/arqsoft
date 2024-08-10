package com.acme.banking.platform.students.application.validators;

import com.acme.banking.platform.students.domain.commands.EditClient;
import com.acme.banking.platform.students.domain.processors.ClientDni;
import com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories.ClientDniRepository;
import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class EditClientValidator {
    private final ClientDniRepository clientDniRepository;
    private static final int DNI_MAX_LENGTH = 8;

    public EditClientValidator(ClientDniRepository clientDniRepository) {
        this.clientDniRepository = clientDniRepository;
    }

    public Notification validate(EditClient command)
    {
        Notification notification = new Notification();

        Long id = command.getId();
        if (id <= 0) notification.addError("Student id must be greater than 0");

        String firstName = command.getFirstName().trim();
        if (firstName.isEmpty()) notification.addError("Student firstname is required");

        String lastName = command.getLastName().trim();
        if (lastName.isEmpty()) notification.addError("Student lastname is required");

        String dni = command.getDni().trim();
        if (dni.isEmpty()) notification.addError("Student dni is required");

        if (dni.length() != DNI_MAX_LENGTH) notification.addError("Student dni must be " + DNI_MAX_LENGTH + " characters");

        if (notification.hasErrors()) return notification;

        Optional<ClientDni> optional = clientDniRepository.findById(id);
        if (optional.isEmpty()) {
            notification.addError("Student not found");
            return notification;
        }

        optional = clientDniRepository.getByDniForDistinctId(dni, id);
        if (optional.isPresent()) notification.addError("Student dni is taken");

        return notification;
    }
}
