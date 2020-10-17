package com.derick.server.controllers;

import com.derick.server.domain.dto.ProcessDTO;
import com.derick.server.domain.entities.Process;
import com.derick.server.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
        List<Process> processList = processService.findAll();
        List<ProcessDTO> processDTOList = processList.stream().map(process -> {
            return processService.toDTO(process);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(processDTOList);
    }

    @GetMapping(value = "/open-process")
    public ResponseEntity<List<ProcessDTO>> findOpenProcesses() {
        List<Process> processList = processService.findByFinalizedFalse();
        List<ProcessDTO> processDTOList = processList.stream().map(process -> {
            return processService.toDTO(process);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(processDTOList);
    }

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<ProcessDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        Page<Process> list = processService.findPage(page, linesPerPage, orderBy, direction);
        Page<ProcessDTO> listDto = list.map(process -> processService.toDTO(process));
        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping(value = "/{id}/responsible-users")
    public ResponseEntity<Void> updateResponsibleUsers(@PathVariable Integer id, @RequestBody ProcessDTO processDTO) {
        Process process = processService.fromDTO(processDTO);
        process.setId(id);
        process = processService.updateResponsibleUsers(process);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/finish-process")
    public ResponseEntity<Void> finishProcess(@PathVariable Integer id, @RequestBody ProcessDTO processDTO) {
        Process process = processService.fromDTO(processDTO);
        process.setId(id);
        process = processService.finishProcess(process);
        return ResponseEntity.noContent().build();
    }
}
