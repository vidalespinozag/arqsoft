package com.acme.banking.platform.students.domain.projections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ClientAuditLog {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long clientId;

    @Column(length=50)
    private String firstName;

    @Column(length=50)
    private String lastName;

    @Column(length=8)
    private String dni;

    @Column(length=8)
    private String status;

    @Column
    private LocalDateTime createdAt;

    public ClientAuditLog() {
    }

    public ClientAuditLog(Long clientId, String firstName, String lastName, String dni, String status, LocalDateTime createdAt) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ClientAuditLog(ClientAuditLog view) {
        this.clientId = view.getClientId();
        this.firstName = view.getFirstName();
        this.lastName = view.getLastName();
        this.dni = view.getDni();
        this.status = view.getStatus();
        this.createdAt = view.getCreatedAt();
    }
}
