package com.acme.banking.platform.accounts.domain.model.aggregates;

import com.acme.banking.platform.accounts.domain.commands.*;
import com.acme.banking.platform.accounts.domain.events.*;
import com.acme.banking.platform.accounts.domain.model.valueobjects.AccountNumber;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.javamoney.moneta.Money;
import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Account {
    @AggregateIdentifier
    private Long id;
    private AccountNumber number;
    private MonetaryAmount balance;
    private MonetaryAmount overdraftLimit;
    private Long clientId;

    public Account() {
    }

    @CommandHandler
    public Account(OpenAccount command) {
        AccountOpened event = new AccountOpened(
            command.getId(),
            command.getNumber(),
            command.getOverdraftLimit(),
            command.getClientId(),
            command.getCreatedAt(),
            command.getCreatedBy()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(EditAccount command) {
        AccountEdited event = new AccountEdited(
            command.getId(),
            command.getOverdraftLimit(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    public void debit(DebitAccount command) {
        MonetaryAmount currentBalance = Money.of(balance.getNumber().doubleValue() + overdraftLimit.getNumber().doubleValue(), "USD");
        if (command.getAmount().doubleValue() <= currentBalance.getNumber().doubleValue()) {
            AccountDebited event = new AccountDebited(
                command.getAccountId(),
                command.getTransactionId(),
                command.getAmount(),
                command.getUpdatedAt()
            );
            apply(event);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        InsufficientFundsDetected event = new InsufficientFundsDetected(
            command.getAccountId(),
            command.getTransactionId(),
            now
        );
        apply(event);
    }

    public void credit(CreditAccount command) {
        LocalDateTime createdAt = LocalDateTime.now();
        AccountCredited event = new AccountCredited(
            command.getAccountId(),
            command.getTransactionId(),
            command.getAmount(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void creditFromAccount(CreditFromAccount command) {
        LocalDateTime createdAt = LocalDateTime.now();
        FromAccountCredited event = new FromAccountCredited(
            command.getAccountId(),
            command.getTransactionId(),
            command.getAmount(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpened event) {
        id = event.getId();
        number = AccountNumber.create(event.getNumber()).getSuccess();
        balance = Money.of(0.00, "USD");
        overdraftLimit = Money.of(event.getOverdraftLimit(), "USD");
        clientId = event.getClientId();
    }

    @EventSourcingHandler
    public void on(AccountEdited event) {
        overdraftLimit = Money.of(event.getOverdraftLimit(), "USD");
    }

    @EventSourcingHandler
    public void on(AccountDebited event) {
        balance = balance.subtract(Money.of(event.getAmount().doubleValue(), "USD"));
    }

    @EventSourcingHandler
    public void on(AccountCredited event) {
        balance = balance.add(Money.of(event.getAmount().doubleValue(), "USD"));
    }

    @EventSourcingHandler
    public void on(FromAccountCredited event) {
        balance = balance.add(Money.of(event.getAmount().doubleValue(), "USD"));
    }
}
