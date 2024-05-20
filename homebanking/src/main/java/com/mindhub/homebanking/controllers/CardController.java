package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(path = "clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> register(Authentication authentication, CardColor cardColor, CardType cardType){
        return cardService.registerCard(authentication, cardColor,cardType);
    }
}
