package com.derick.server.services;

import com.derick.server.domain.dto.NewClientDTO;
import com.derick.server.domain.dto.ClientDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.domain.enums.ClientRole;
import com.derick.server.repositories.UserRepository;
import com.derick.server.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Client findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id: " + id + " wasn't found!"));
    }

    public List<Client> findAll() {
        return userRepository.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return userRepository.findAll(pageRequest);
    }

    public Client insert(Client client) {
        client.setId(null);
        client = userRepository.save(client);
        return client;
    }

    public Client update(Client client) {
        Client newClient = findById(client.getId());
        updateUserData(newClient, client);
        return userRepository.save(newClient);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("It isn't possible to delete a entity with relationships to another entities");
        }
    }

    private void updateUserData(Client newObj, Client obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Client fromDTO(NewClientDTO dto) {
        Client client = new Client(null, dto.getName(), dto.getEmail(), ClientRole.toEnum(dto.getUserType()), bCryptPasswordEncoder.encode(dto.getPassword()));
        return client;
    }

    public Client fromDTO(ClientDTO dto) {
        Client client = new Client(dto.getName(), dto.getEmail());
        return client;
    }


}
