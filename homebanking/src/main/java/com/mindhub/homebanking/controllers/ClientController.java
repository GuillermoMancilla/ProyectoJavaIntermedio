package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;



    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getAllClient();
    }

    @RequestMapping("/clients/{Id}")
    public ClientDTO getClient(@PathVariable Long Id){
        return clientService.getClientById(Id);

    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        return clientService.getCurrent(authentication);
    }


}
