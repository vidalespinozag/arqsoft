package com.acme.banking.platform.transactions.interfaces.rest.resources;

import java.math.BigDecimal;

public record TransferMoneyResource(
    Long fromAccountId,
    Long toAccountId,
    BigDecimal amount
) {}
