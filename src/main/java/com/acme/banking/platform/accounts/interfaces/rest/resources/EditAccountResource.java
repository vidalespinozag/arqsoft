package com.acme.banking.platform.accounts.interfaces.rest.resources;

import java.math.BigDecimal;

public record EditAccountResource(
    Long accountId,
    BigDecimal overdraftLimit
) {}
