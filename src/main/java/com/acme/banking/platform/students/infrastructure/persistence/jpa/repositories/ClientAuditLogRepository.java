package com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.students.domain.projections.ClientAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientAuditLogRepository extends JpaRepository<ClientAuditLog, Long> {
    @Query(value = "SELECT * FROM client_audit_logs WHERE client_id = :clientId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<ClientAuditLog> getLastByClientId(@Param("clientId") Long clientId);

    @Query(value = "SELECT * FROM client_audit_logs WHERE client_id = :clientId ORDER BY created_at", nativeQuery = true)
    List<ClientAuditLog> getHistoryByClientId(Long clientId);
}
