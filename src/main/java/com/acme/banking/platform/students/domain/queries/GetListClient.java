package com.acme.banking.platform.students.domain.queries;

import lombok.Value;

@Value
public class GetListClient {
    private final Long page;
    private final Long limit;

    public GetListClient(Long page, Long limit) {
        this.page = page;
        this.limit = limit;
    }
}
