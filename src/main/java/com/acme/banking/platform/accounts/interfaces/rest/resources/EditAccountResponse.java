package com.acme.banking.platform.accounts.interfaces.rest.resources;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class EditAccountResponse {
    private Long accountId;
    private BigDecimal overdraftLimit;
}
