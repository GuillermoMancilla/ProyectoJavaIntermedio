package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired

    private PasswordEncoder passwordEncoder;

    public List<AccountDTO> getAllAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    public AccountDTO getAccountById(Long Id){
        return accountRepository.findById(Id).map(AccountDTO::new).orElse(null);
    }

    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);


        String newNumberAccount = "VIN-" + numberRandom(100000, 999999);
        Account newAccount = new Account(newNumberAccount, LocalDate.now(),0);
        client.addClient(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public  ResponseEntity<Object> registeracc(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size()>=3){
            return new ResponseEntity<>("client already has 3 accounts",HttpStatus.FORBIDDEN);
        }

        String newNumberAccount = "VIN-" + numberRandom(100000, 999999);

        Account newAccount = new Account(newNumberAccount, LocalDate.now(),0);
        client.addClient(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public List<AccountDTO>getCurrentAccounts(Authentication authentication)
    {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }
    private int numberRandom(int min, int max){
        return min + (int) (Math.random() * (max - min + 1));
    }
}
