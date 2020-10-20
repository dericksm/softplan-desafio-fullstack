package com.derick.server.controllers;

import com.derick.server.models.dto.ClientDTO;
import com.derick.server.models.dto.NewClientDTO;
import com.derick.server.models.entities.Client;
import com.derick.server.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Client> insert(@Valid @RequestBody NewClientDTO userDTO) {
        Client client = clientService.fromDTO(userDTO);
        client = clientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        ClientDTO clientDTO = new ClientDTO(client);
        return ResponseEntity.ok().body(clientDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clientList = clientService.findAll();
        List<ClientDTO> clientDTOList = clientList.stream().map(user -> new ClientDTO(user)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientDTOList);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> update(@RequestBody ClientDTO clientDTO, @PathVariable Integer id) {
        Client client = clientService.fromDTO(clientDTO);
        client.setId(id);
        client = clientService.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
