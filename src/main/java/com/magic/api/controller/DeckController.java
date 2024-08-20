package com.magic.api.controller;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeckController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/validate-commander")
    public ResponseEntity<Card> validateCommander(@RequestParam String cardId) {
        String url = "https://api.scryfall.com/cards/" + cardId;

        Card response = restTemplate.getForObject(url, Card.class);

//        if (response == null) {
//            return ResponseEntity.status(404).body("Card not found.");
//        }

        Map<String, String> legalities = response.getLegalities();
        String commanderLegality = legalities.get("commander");

        if ("legal".equals(commanderLegality)) {
            Deck newDeck = new Deck(response);

            System.out.println(newDeck.getCommander());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
}
