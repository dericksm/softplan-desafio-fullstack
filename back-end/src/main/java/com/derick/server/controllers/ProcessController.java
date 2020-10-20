package com.derick.server.controllers;

import com.derick.server.models.dto.ClientDTO;
import com.derick.server.models.dto.ProcessDTO;
import com.derick.server.models.entities.Client;
import com.derick.server.models.entities.Process;
import com.derick.server.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRIADOR')")
    public ResponseEntity<ProcessDTO> insert(@RequestBody ProcessDTO processDTO) {
        Process process = processService.fromDTO(processDTO);
        process = processService.insert(process);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(process.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Process> findById(@PathVariable Integer id) {
        Process process = processService.findById(id);
        return ResponseEntity.ok().body(process);
    }

    @GetMapping
    public ResponseEntity<List<ProcessDTO>> findAll() {
        // filter process by client if it's a finisher role
        List<Process> processList = processService.findAllFiltered();
        List<ProcessDTO> processDTOList = processList.stream()
                .map(process -> {
                    return processService.toDTO(process);
                }).collect(Collectors.toList());
        return ResponseEntity.ok().body(processDTOList);
    }

    @PutMapping(value = "/{id}/responsible-clients")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRIADOR')")
    public ResponseEntity<Void> updateResponsibleUsers(@PathVariable Integer id, @RequestBody List<ClientDTO> userListDTO) {
        List<Client> clientList = processService.mapUserDTO(userListDTO);
        processService.updateResponsibleUsers(id, clientList);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/add-feedback")
    public ResponseEntity<Void> addFeedbackToProcess(@PathVariable Integer id, @RequestBody ProcessDTO processDTO) {
        Process process = processService.fromDTO(processDTO);
        process.setFeedback(processDTO.getFeedback());
        process = processService.update(process);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/finish-process")
    public ResponseEntity<Void> finishProcess(@PathVariable Integer id) {
        Process process = processService.findById(id);
        process = processService.finishProcess(process);
        return ResponseEntity.noContent().build();
    }
}
