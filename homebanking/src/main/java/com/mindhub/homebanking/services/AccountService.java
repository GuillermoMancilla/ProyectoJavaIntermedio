package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long Id);
}
