package com.magic.api.service;

import com.magic.api.domain.Card;
import com.magic.api.domain.Color;
import com.magic.api.domain.Deck;
import com.magic.api.repository.DeckRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class DeckService {

    private final RestTemplate restTemplate;
    private final DeckRespository deckRespository;

    public DeckService(RestTemplate restTemplate, DeckRespository deckRespository) {
        this.restTemplate = restTemplate;
        this.deckRespository = deckRespository;
    }

    public Card fetchCard(String cardId) {
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

    public boolean validateCommonCard(String cardId) {

        return validateCard(cardId);

    }

    public Deck fetchDeck(String deckId) {

        return deckRespository.findById(deckId).orElse(null);

    }

    public Deck addCardOnDeck(Card card, Deck deck) {

            deck.getCards().add(card);
            return deckRespository.save(deck);
    }

    public boolean validateColorCardOnDeck(Card card, Deck deck) {

        List<Color> deckColors = deck.getColors();
        List<Color> cardColors = card.getColors();
        return new HashSet<>(deckColors).containsAll(cardColors);
    }
}
