package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<AccountDTO> getAllAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    public AccountDTO getAccountById(Long Id){
        return accountRepository.findById(Id).map(AccountDTO::new).orElse(null);
    }
}
