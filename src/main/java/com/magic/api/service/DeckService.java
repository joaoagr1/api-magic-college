package com.magic.api.service;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
public class DeckService {

    private final RestTemplate restTemplate;

    public DeckService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Card fetchCard(String cardId) {
        String url = "https://api.scryfall.com/cards/" + cardId;
        try {
            return restTemplate.getForObject(url, Card.class);
        } catch (RestClientException e) {
            return null;
        }
    }

    public boolean validateCardAsCommander(String commanderId) {
        Card card = fetchCard(commanderId);
        if (card == null) {
            return false;
        }
        Map<String, String> legalities = card.getLegalities();
        return "legal".equals(legalities.get("commander"));
    }

    public boolean validateCard(String commanderId) {
        return fetchCard(commanderId) != null;
    }

    public boolean validateCommander(String commanderId) {
        return validateCard(commanderId) && validateCardAsCommander(commanderId);
    }

    public Card getCommander(String commanderId) {
        return fetchCard(commanderId);
    }

    public Deck createDeck(Card commander) {
        return new Deck(commander);
    }
}
