package com.acme.banking.platform.students.application.querygateways;

import com.acme.banking.platform.students.domain.projections.Client;
import com.acme.banking.platform.students.domain.projections.ClientAuditLog;
import com.acme.banking.platform.students.domain.queries.GetListClientAuditLogByClientId;
import com.acme.banking.platform.students.domain.queries.GetListClient;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClientQueryService {
    private final QueryGateway queryGateway;

    public ClientQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public List<ClientAuditLog> queryClientLogAudit(Long clientId) throws Exception {
        var query = new GetListClientAuditLogByClientId(clientId);
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ClientAuditLog.class)).join();
    }

    public List<Client> queryListClient(Long page, Long limit) throws Exception {
        page = page <= 0 ? 1 : page;
        limit = (limit <= 0 || limit > 100) ? 100 : limit;
        var query = new GetListClient(page, limit);
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(Client.class)).join();
    }
}
