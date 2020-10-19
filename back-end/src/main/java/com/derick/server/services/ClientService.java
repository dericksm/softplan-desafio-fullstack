package com.derick.server.services;

import com.derick.server.domain.dto.ClientDTO;
import com.derick.server.domain.dto.NewClientDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.domain.enums.ClientRole;
import com.derick.server.repositories.ClientRepository;
import com.derick.server.services.exceptions.DataIntegrityException;
import com.derick.server.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Client findById(Integer id) {
        return clientRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente com id: " + id + " não encontrado!"));
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client insert(Client client) {
        client.setId(null);
        client = clientRepository.save(client);
        return client;
    }

    public Client update(Client client) {
        Client newClient = findById(client.getId());
        updateUserData(newClient, client);
        return clientRepository.save(newClient);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível deletar um cliente associado a um processo");
        }
    }

    private void updateUserData(Client newObj, Client obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Client fromDTO(NewClientDTO dto) {
        Client client = new Client(null, dto.getName(), dto.getEmail(), ClientRole.toEnum(dto.getRole()), bCryptPasswordEncoder.encode(dto.getPassword()));
        return client;
    }

    public Client fromDTO(ClientDTO dto) {
        Client client = new Client(dto.getName(), dto.getEmail());
        return client;
    }


}
