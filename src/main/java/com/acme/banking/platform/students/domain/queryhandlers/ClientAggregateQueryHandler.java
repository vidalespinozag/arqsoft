package com.acme.banking.platform.students.domain.queryhandlers;

import com.acme.banking.platform.students.domain.projections.Client;
import com.acme.banking.platform.students.domain.projections.ClientAuditLog;
import com.acme.banking.platform.students.domain.queries.GetListClientAuditLogByClientId;
import com.acme.banking.platform.students.domain.queries.GetListClient;
import com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories.ClientAuditLogRepository;
import com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClientAggregateQueryHandler {
    private final ClientRepository clientRepository;
    private final ClientAuditLogRepository clientAuditRepository;

    public ClientAggregateQueryHandler(ClientRepository clientRepository, ClientAuditLogRepository clientAuditRepository) {
        this.clientRepository = clientRepository;
        this.clientAuditRepository = clientAuditRepository;
    }

    @QueryHandler
    public List<ClientAuditLog> handle(GetListClientAuditLogByClientId query) {
        return this.clientAuditRepository.getHistoryByClientId(query.getId());
    }

    @QueryHandler
    public List<Client> handle(GetListClient query) {
        return this.clientRepository.getPaginated(query.getPage(), query.getLimit());
    }
}
