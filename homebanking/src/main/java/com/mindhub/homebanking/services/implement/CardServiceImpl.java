package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Object> registerCard(Authentication authentication, CardColor cardColor, CardType cardType){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().size()>=3){
            return new ResponseEntity<>("client already has 3 cards", HttpStatus.FORBIDDEN);
        }

        String newNumbercard =  numberRandom(1000, 9999) + "-" + numberRandom(1000, 9999) + "-" +
                numberRandom(1000, 9999) + "-" + numberRandom(1000, 9999);

        Card card1 = new Card(client.getFirstName()+" "+client.getLastName(), cardType,
                cardColor,newNumbercard,numberRandom(100,999),LocalDate.now(),
                LocalDate.now().plusYears(5),client);

        cardRepository.save(card1);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    private int numberRandom(int min, int max){
        return min + (int) (Math.random() * (max - min + 1));
    }
}
