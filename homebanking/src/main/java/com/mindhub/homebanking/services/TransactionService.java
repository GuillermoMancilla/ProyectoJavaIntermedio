package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


public interface TransactionService {
    ResponseEntity<Object> registerTransaction(String fromAccountNumber, String toAccountNumber, double amount, String description, Authentication authentication);
}
