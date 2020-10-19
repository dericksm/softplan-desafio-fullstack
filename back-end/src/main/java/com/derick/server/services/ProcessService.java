package com.derick.server.services;

import com.derick.server.domain.dto.ClientDTO;
import com.derick.server.domain.dto.ProcessDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.domain.entities.Process;
import com.derick.server.domain.enums.ClientRole;
import com.derick.server.repositories.ProcessRepository;
import com.derick.server.security.UserSS;
import com.derick.server.services.exceptions.DataIntegrityException;
import com.derick.server.services.exceptions.ObjectNotFoundException;
import com.derick.server.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    public Process findById(Integer id) {
        return processRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Processo com: " + id + " não encontrado"));
    }

    public List<Process> findAllFiltered() {
        UserSS currentUser = userService.authenticatedUser();
        if (currentUser.hasRole(ClientRole.USER_FINISHER)) {
            Client client = clientService.findById(currentUser.getId());
            return processRepository.findByResponsibleClients(client);
        } else {
            return processRepository.findAll();
        }
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public Process insert(Process process) {
        process.setId(null);
        process = processRepository.save(process);
        return process;
    }

    public Process update(Process process) {
        process = processRepository.save(process);
        return process;
    }

    public Process updateResponsibleUsers(Integer id, List<Client> clients) {
        Process newProcess = findById(id);
        newProcess.setResponsibleClients(clients);
        return processRepository.save(newProcess);
    }

    public Process finishProcess(Process process) {
        if (process.getFeedback() == null) {
            throw new DataIntegrityException("Não é possível finalizar um processo sem seu feedback");
        }
        process.setFinalized(true);
        return processRepository.save(process);
    }

    public Process fromDTO(ProcessDTO dto) {
        List<Client> clientList = mapUserDTO(dto.getResponsibleClients());
        Process process = new Process(dto.getName(), dto.getFeedback());
        process.getResponsibleClients().addAll(clientList);
        return process;
    }

    public List<Client> mapUserDTO(List<ClientDTO> responsibleUsersDTO) {
        return responsibleUsersDTO.stream().map(clientDTO -> {
            return clientService.findById(clientDTO.getId());
        }).collect(Collectors.toList());
    }

    public List<ClientDTO> mapToUserDTO(List<Client> responsibleClients) {
        return responsibleClients.stream().map(user -> {
            return new ClientDTO(user);
        }).collect(Collectors.toList());
    }

    public ProcessDTO toDTO(Process process) {
        ProcessDTO processDTO = new ProcessDTO(process);
        processDTO.getResponsibleClients().addAll(mapToUserDTO(process.getResponsibleClients()));
        return processDTO;
    }

}
