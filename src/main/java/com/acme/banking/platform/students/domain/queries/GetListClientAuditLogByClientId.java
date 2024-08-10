package com.acme.banking.platform.students.domain.queries;

import lombok.Value;

@Value
public class GetListClientAuditLogByClientId {
    private final Long id;

    public GetListClientAuditLogByClientId(Long id) {
        this.id = id;
    }
}
