package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CardService {
    public ResponseEntity<Object> registerCard(Authentication authentication, CardColor cardColor, CardType cardType);
}
