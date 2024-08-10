package com.acme.banking.platform.accounts.interfaces.rest.resources;

import java.math.BigDecimal;

public record OpenAccountResource(
    String number,
    BigDecimal overdraftLimit,
    Long clientId
) {}
