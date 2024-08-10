package com.acme.banking.platform.accounts.domain.queries;

import lombok.Value;

@Value
public class GetPaginatedAccounts {
    private final Long page;
    private final Long limit;

    public GetPaginatedAccounts(Long page, Long limit) {
        this.page = page;
        this.limit = limit;
    }
}
