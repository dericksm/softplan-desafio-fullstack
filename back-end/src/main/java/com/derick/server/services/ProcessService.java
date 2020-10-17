package com.derick.server.services;

import com.derick.server.domain.dto.ProcessDTO;
import com.derick.server.domain.dto.ClientDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.domain.entities.Process;
import com.derick.server.repositories.ProcessRepository;
import com.derick.server.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    public Process findById(Integer id) {
        return processRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Process with id: " + id + " wasn't found!"));
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public Page<Process> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return processRepository.findAll(pageRequest);
    }

    public Process insert(Process process) {
        process.setId(null);
        process = processRepository.save(process);
        return process;
    }

    public Process updateResponsibleUsers(Process process) {
        Process newProcess = findById(process.getId());
        newProcess.getResponsibleClients().addAll(process.getResponsibleClients());
        return processRepository.save(newProcess);
    }

    public Process finishProcess(Process process) {
        Process newProcess = findById(process.getId());
        newProcess.setFeedback(process.getFeedback());
        newProcess.setFinalized(true);
        return processRepository.save(newProcess);
    }

    public List<Process> findByFinalizedFalse(){
        return processRepository.findByFinalizedFalse();
    }

    public Process fromDTO(ProcessDTO dto) {
        List<Client> clientList = mapUserDTO(dto.getResponsibleUsers());
        Process process = new Process(dto.getFeedback());
        process.getResponsibleClients().addAll(clientList);
        return process;
    }

    public List<Client> mapUserDTO(List<ClientDTO> responsibleUsersDTO){
        return responsibleUsersDTO.stream().map(clientDTO -> {
            return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail());
        }).collect(Collectors.toList());
    }

    public List<ClientDTO> mapToUserDTO(List<Client> responsibleClients){
        return responsibleClients.stream().map(user -> {
            return new ClientDTO(user);
        }).collect(Collectors.toList());
    }

    public ProcessDTO toDTO(Process process){
        ProcessDTO processDTO = new ProcessDTO(process);
        processDTO.getResponsibleUsers().addAll(mapToUserDTO(process.getResponsibleClients()));
        return processDTO;
    }

}
