package com.magic.api.service;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DeckService {

    private final RestTemplate restTemplate;

    public DeckService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public boolean validateCardAsCommander(String commanderId) {

        String url = "https://api.scryfall.com/cards/" + commanderId;

        try {
            Card response = restTemplate.getForObject(url, Card.class);

            if (response == null) {
                return false;
            }

            Map<String, String> legalities = response.getLegalities();
            String commanderLegality = legalities.get("commander");

            return "legal".equals(commanderLegality);
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return false;
        } catch (Exception e) {

            return false;
        }

    }


    public boolean validateCard(String commanderId) {

        String url = "https://api.scryfall.com/cards/" + commanderId;

        try {
            Card response = restTemplate.getForObject(url, Card.class);

            return response != null;
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return false;
        } catch (Exception e) {

            return false;
        }


    }

    public boolean validateCommander(String commanderId) {

        boolean cardExists = validateCard(commanderId);
        boolean isCommander = validateCardAsCommander(commanderId);

        return cardExists && isCommander;
    }

    public Card getCommander(String commanderId) {

        String url = "https://api.scryfall.com/cards/" + commanderId;

        try {
            return restTemplate.getForObject(url, Card.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return null;
        } catch (Exception e) {

            return null;
        }
    }

    public Deck createDeck(Card commander) {

        Deck newDeck = new Deck(commander);

        return newDeck;
    }
}
