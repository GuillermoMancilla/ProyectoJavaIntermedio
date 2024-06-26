package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired

    private PasswordEncoder passwordEncoder;

    public List<ClientDTO> getAllClient(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    public ClientDTO getClientById(Long Id){
        //Client client = clientRepository.findById(Id).orElse(null);
        return clientRepository.findById(Id).map(ClientDTO::new).orElse(null);
    }

    public ClientDTO getCurrent(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }


}
