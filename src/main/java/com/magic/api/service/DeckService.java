package com.magic.api.service;

import com.magic.api.domain.Card;
import com.magic.api.domain.Color;
import com.magic.api.domain.Deck;
import com.magic.api.repository.CardRepository;
import com.magic.api.repository.DeckRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CardRepository cardRepository;

    @Autowired
    public DeckService(RestTemplate restTemplate, DeckRespository deckRespository, CardRepository cardRepository) {
        this.restTemplate = restTemplate;
        this.deckRespository = deckRespository;
        this.cardRepository = cardRepository;
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
        return "legal".equals(legalities.get("commander")) && validateTypeLine(card);
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

    @Transactional
    public Deck createDeck(Card commander) {
        // Save the commander card first if it's not already saved
        Card savedCommander = cardRepository.findById(commander.getId()).orElseGet(() -> cardRepository.save(commander));

        Deck newDeck = new Deck(savedCommander);
        deckRespository.save(newDeck);

        return newDeck;
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

    public boolean validateTypeLine(Card card) {
        String typeLine = card.getTypeLine();
        return typeLine != null && typeLine.contains("Legendary Creature");
    }
}