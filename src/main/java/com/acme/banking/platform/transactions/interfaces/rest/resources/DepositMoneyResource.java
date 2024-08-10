package com.acme.banking.platform.transactions.interfaces.rest.resources;

import java.math.BigDecimal;

public record DepositMoneyResource(
    Long accountId,
    BigDecimal amount
) {}
