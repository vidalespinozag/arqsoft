package com.acme.banking.platform.students.interfaces.eventhandlers;

import com.acme.banking.platform.students.domain.events.ClientEdited;
import com.acme.banking.platform.students.domain.events.ClientRegistered;
import com.acme.banking.platform.students.domain.model.valueobjects.ClientStatus;
import com.acme.banking.platform.students.domain.projections.ClientAuditLog;
import com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories.ClientAuditLogRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ClientAuditProjectionsEventHandler {
    private final ClientAuditLogRepository clientAuditRepository;

    public ClientAuditProjectionsEventHandler(ClientAuditLogRepository clientAuditRepository) {
        this.clientAuditRepository = clientAuditRepository;
    }

    @EventHandler
    public void on(ClientRegistered event) {
        ClientAuditLog view = new ClientAuditLog(
            event.getId(),
            event.getFirstName(),
            event.getLastName(),
            event.getDni(),
            ClientStatus.ACTIVE.name(),
            event.getCreatedAt());
        clientAuditRepository.save(view);
    }

    @EventHandler
    public void on(ClientEdited event) {
        Optional<ClientAuditLog> viewOptional = clientAuditRepository.getLastByClientId(event.getId());
        if (viewOptional.isPresent()) {
            ClientAuditLog lastClientAudit = viewOptional.get();
            ClientAuditLog clientAudit = new ClientAuditLog(lastClientAudit);
            clientAudit.setFirstName(event.getFirstName());
            clientAudit.setLastName(event.getLastName());
            clientAudit.setDni(event.getDni());
            clientAudit.setCreatedAt(event.getUpdatedAt());
            clientAuditRepository.save(clientAudit);
        }
    }
}
