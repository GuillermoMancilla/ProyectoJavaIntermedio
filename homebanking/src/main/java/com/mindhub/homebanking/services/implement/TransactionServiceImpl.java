package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<Object> registerTransaction(String fromAccountNumber, String toAccountNumber, double amount, String description, Authentication authentication) {
        if (fromAccountNumber.isEmpty() || toAccountNumber.isEmpty() || description.isEmpty() || amount == 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Account accFrom = accountRepository.findBynumber(fromAccountNumber);
        Account accTo = accountRepository.findBynumber(toAccountNumber);

        if (accFrom ==  null) {
            return new ResponseEntity<>("The origin account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (accTo ==  null) {
            return new ResponseEntity<>("The destine account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("To account and from account can't be the same", HttpStatus.FORBIDDEN);
        }

        Client client = clientRepository.findByEmail(authentication.getName());

        boolean exist = false;
        for(Account acc : client.getAccounts()){
            if (fromAccountNumber.equals(acc.getNumber())) {
                exist = true;

            }
        }

        if (exist == false) {
            return new ResponseEntity<>("To account doesn't exist for this client", HttpStatus.FORBIDDEN);
        }

        if (accFrom.getBalance() < amount) {
            return new ResponseEntity<>("To account doesn't have founds", HttpStatus.FORBIDDEN);
        }

        Transaction trxCredit = new Transaction(TransactionType.CREDIT,amount, description,LocalDate.now());
        Transaction trxDebit = new Transaction(TransactionType.DEBIT, -amount,description,LocalDate.now());

        accFrom.addAccount(trxDebit);
        accTo.addAccount(trxCredit);

        transactionRepository.save(trxDebit);
        transactionRepository.save(trxCredit);

        accFrom.setBalance(accFrom.getBalance() - amount);
        accTo.setBalance(accTo.getBalance() + amount);

        accountRepository.save(accFrom);
        accountRepository.save(accTo);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
