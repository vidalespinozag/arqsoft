package com.acme.banking.platform.transactions.domain.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.time.LocalDateTime;

@Value
public class MarkTransferAsFailed {
    @TargetAggregateIdentifier
    private Long transactionId;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
