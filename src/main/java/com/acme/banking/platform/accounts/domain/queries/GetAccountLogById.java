package com.acme.banking.platform.accounts.domain.queries;

import lombok.Value;

@Value
public class GetAccountLogById {
    private final Long id;

    public GetAccountLogById(Long id) {
        this.id = id;
    }
}
