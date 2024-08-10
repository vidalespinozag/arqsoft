package com.acme.banking.platform.students.domain.events;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ClientRegistered {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private LocalDateTime createdAt;
}
