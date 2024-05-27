package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){ this.transactionService = transactionService;}

    @RequestMapping("/transactions")
    public ResponseEntity<Object> registerTransaction(

            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,

            @RequestParam double amount, @RequestParam String description,
            Authentication authentication) {

        return transactionService.registerTransaction(fromAccountNumber, toAccountNumber, amount, description, authentication);
    }
}
