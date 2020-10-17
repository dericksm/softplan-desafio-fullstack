package com.derick.server.repositories;

import com.derick.server.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, Integer> {

    Client findByEmail(String email);
}
