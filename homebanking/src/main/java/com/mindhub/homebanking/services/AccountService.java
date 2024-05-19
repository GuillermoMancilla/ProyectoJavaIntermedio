package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long Id);

    ResponseEntity<Object> register(String firstName, String lastName, String email, String password);

    public  ResponseEntity<Object> registeracc(Authentication authentication);
}
