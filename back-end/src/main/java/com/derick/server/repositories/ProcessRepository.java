package com.derick.server.repositories;

import com.derick.server.models.entities.Client;
import com.derick.server.models.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    List<Process> findByResponsibleClients(Client client);
}
