package com.derick.server.config;

import com.derick.server.domain.entities.Client;
import com.derick.server.domain.enums.ClientRole;
import com.derick.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DbConfig implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Client client = new Client(null, "Derick Souza", "derick_sm@hotmail.com", ClientRole.ADMIN, bCryptPasswordEncoder.encode("123456"));
        clientRepository.save(client);
    }
}
