package com.acme.banking.platform.students.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.students.domain.projections.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    @Procedure(procedureName = "get_clients")
    List<Client> getPaginated(long page, long limit);

    Optional<Client> getByDni(String dni);

    @Query(value = "SELECT * FROM clients WHERE id <> :id AND dni = :dni", nativeQuery = true)
    Optional<Client> getByIdAndDni(String id, String dni);
}
