package com.derick.server.controllers;

import com.derick.server.domain.dto.NewClientDTO;
import com.derick.server.domain.dto.ClientDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> insert(@RequestBody NewClientDTO userDTO) {
        Client client = clientService.fromDTO(userDTO);
        client = clientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok().body(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clientList = clientService.findAll();
        List<ClientDTO> clientDTOList = clientList.stream().map(user -> new ClientDTO(user)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientDTOList);
    }

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        Page<Client> list = clientService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientDTO> listDto = list.map(obj -> new ClientDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody ClientDTO clientDTO, @PathVariable Integer id) {
        Client client = clientService.fromDTO(clientDTO);
        client.setId(id);
        client = clientService.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
