package com.acme.banking.platform.accounts.interfaces.rest.resources;

import java.math.BigDecimal;

public record AccountResource(
    Long id,
    String number,
    BigDecimal balance,
    BigDecimal overdraftLimit,
    Long clientId
) {}
