package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;

import java.util.List;

public interface ClientService{

    List<ClientDTO> getAllClient();

    ClientDTO getClientById(Long Id);
}
