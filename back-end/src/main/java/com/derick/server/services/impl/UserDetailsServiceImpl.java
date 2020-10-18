package com.derick.server.services.impl;

import com.derick.server.domain.entities.Client;
import com.derick.server.repositories.ClientRepository;
import com.derick.server.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Client client = repository.findByEmail(email);
		if (client == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(client.getId(), client.getEmail(), client.getPassword(), client.getRole());
	}
}