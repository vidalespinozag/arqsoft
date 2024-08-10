package com.acme.banking.platform.transactions.interfaces.rest.resources;

import java.math.BigDecimal;

public record WithdrawMoneyResource(
    Long accountId,
    BigDecimal amount
) {}
