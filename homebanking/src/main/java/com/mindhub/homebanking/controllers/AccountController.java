package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAllAccounts();
    }

    @RequestMapping("/accounts/{Id}")
    public AccountDTO getAccount(@PathVariable Long Id){
        return accountService.getAccountById(Id);

    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        return accountService.register(firstName, lastName, email, password);
    }

    @RequestMapping(path = "clients/current/accounts", method = RequestMethod.POST)
    public  ResponseEntity<Object> register(Authentication authentication){
        return accountService.registeracc(authentication);
    }

    @RequestMapping(path = "clients/current/accounts")
    public  List<AccountDTO> getAccounts(Authentication authentication){
        return accountService.getCurrentAccounts(authentication);
    }
}
