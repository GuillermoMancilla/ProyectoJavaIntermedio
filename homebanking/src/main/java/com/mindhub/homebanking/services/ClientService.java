package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService{

    List<ClientDTO> getAllClient();

    ClientDTO getClientById(Long Id);

    ClientDTO getCurrent(Authentication authentication);

    ResponseEntity<Object> register(String firstName, String lastName, String email, String password);
}
