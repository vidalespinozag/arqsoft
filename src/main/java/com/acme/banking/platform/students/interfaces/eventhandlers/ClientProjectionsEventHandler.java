package com.acme.banking.platform.students.interfaces.eventhandlers;

import com.acme.banking.platform.students.domain.events.ClientEdited;
import com.acme.banking.platform.students.domain.events.ClientRegistered;
import com.acme.banking.platform.students.domain.model.valueobjects.ClientStatus;
import com.acme.banking.platform.students.domain.projections.Client;
import com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ClientProjectionsEventHandler {
    private final ClientRepository clientRepository;

    public ClientProjectionsEventHandler(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @EventHandler
    public void on(ClientRegistered event) {
        Client client = new Client(
            event.getId(),
            event.getFirstName(),
            event.getLastName(),
            event.getDni(),
            ClientStatus.ACTIVE.name(),
            event.getCreatedAt());
        clientRepository.save(client);
    }

    @EventHandler
    public void on(ClientEdited event) {
        Optional<Client> viewOptional = clientRepository.findById(event.getId().toString());
        if (viewOptional.isPresent()) {
            Client view = viewOptional.get();
            view.setFirstName(event.getFirstName());
            view.setLastName(event.getLastName());
            view.setDni(event.getDni());
            view.setUpdatedAt(event.getUpdatedAt());
            clientRepository.save(view);
        }
    }
}
