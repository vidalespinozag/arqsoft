package com.acme.banking.platform.students.interfaces.rest;

import com.acme.banking.platform.students.application.commandgateways.ClientCommandService;
import com.acme.banking.platform.students.application.querygateways.ClientQueryService;
import com.acme.banking.platform.students.domain.projections.Client;
import com.acme.banking.platform.students.domain.projections.ClientAuditLog;
import com.acme.banking.platform.students.interfaces.rest.resources.*;
import com.acme.banking.platform.students.interfaces.rest.transform.ClientResourceFromCommandAssembler;
import com.acme.banking.platform.students.interfaces.rest.transform.EditClientCommandFromResourceAssembler;
import com.acme.banking.platform.students.interfaces.rest.transform.RegisterClientCommandFromResourceAssembler;
import com.acme.banking.platform.shared.interfaces.rest.PaginationMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hypersistence.tsid.TSID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student Management Endpoints")
public class ClientsController {
    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientsController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @Operation(summary = "Register a new student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<RegisterClientResponseResource> register(@RequestBody RegisterClientResource resource) {
        try {
            Long id = TSID.Factory.getTsid().toLong();
            resource = resource.withId(id);
            var command = RegisterClientCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = clientCommandService.register(command);
            if (notification.hasErrors()) {
                var response = new RegisterClientResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var clientResource = ClientResourceFromCommandAssembler.toResourceFromRegisterClient(command);
            var response = new RegisterClientResponseResource(clientResource, new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RegisterClientResponseResource(null, new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Edit a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student edited successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditClientResponseResource> edit(@PathVariable("id") Long id, @RequestBody EditClientResource resource) {
        try {
            resource = resource.withId(id);
            var command = EditClientCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = clientCommandService.edit(command);
            if (notification.hasErrors()) {
                var response = new EditClientResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var clientResource = ClientResourceFromCommandAssembler.toResourceFromEditClient(command);
            var response = new EditClientResponseResource(clientResource, new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EditClientResponseResource(null, new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/audit-log")
    @Operation(summary = "Get Student audit log")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = ""),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientAuditLogResponseResource> GetAuditLogByClientId(@PathVariable("id") Long clientId) {
        try {
            List<ClientAuditLog> auditLog = clientQueryService.queryClientLogAudit(clientId);
            var response = new ClientAuditLogResponseResource(auditLog, new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ClientAuditLogResponseResource(null, new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page/{page}/limit/{limit}")
    @Operation(summary = "Get paginated students")
    public ResponseEntity<ListClientResponseResource> GetClients(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        try {
            List<Client> clients = clientQueryService.queryListClient(page, limit);
            var response = new ListClientResponseResource(clients, new ArrayList<>());

            PaginationMetadata paginationMetadata = new PaginationMetadata(clients.size(), page, limit);
            ObjectMapper objectMapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Pagination", objectMapper.writeValueAsString(paginationMetadata));

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ListClientResponseResource(null, new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
