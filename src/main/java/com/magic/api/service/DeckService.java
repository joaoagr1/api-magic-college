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

import java.util.*;
import java.util.stream.Collectors;

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
        Card savedCommander = cardRepository.findById(commander.getCardId()).orElseGet(() -> cardRepository.save(commander));

        Deck newDeck = new Deck(savedCommander);
        deckRespository.save(newDeck);

        addAllCardsRandomOnDeck(newDeck);

        return newDeck;
    }

    private void addAllCardsRandomOnDeck(Deck newDeck) {
        List<Color> deckColors = newDeck.getCommander().getColors();
        List<Color> allColors = Arrays.asList(Color.values());
        Set<Color> excludedColors = new HashSet<>(allColors);
        excludedColors.removeAll(deckColors);

        String query = excludedColors.stream()
                .map(color -> "-c:" + color.name().substring(0, 1).toLowerCase())
                .collect(Collectors.joining(" "));

        String url = "https://api.scryfall.com/cards/random?q=" + query;
        System.out.println("Constructed URL: " + url);

        for (int i = 0; i < 99  ; i++) {
            try {
                Card card = restTemplate.getForObject(url, Card.class);
                if (card != null && checkForDuplicateCardIds(newDeck, card)) {
                    addCardOnObjectDeck(card, newDeck);
                    System.out.println(i);
                }
            } catch (RestClientException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCardOnObjectDeck(Card card, Deck newDeck) {
        // Save the card if it's not already saved
        Card savedCard = cardRepository.findById(card.getCardId()).orElseGet(() -> cardRepository.save(card));
        newDeck.getCards().add(savedCard);
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

    public boolean checkForDuplicateCardIds(Deck deck, Card card) {
        return deck.getCards().stream().noneMatch(c -> c.getCardId().equals(card.getCardId()));
    }
}