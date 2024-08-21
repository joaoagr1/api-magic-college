package com.magic.api.controller;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import com.magic.api.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/deck")
public class DeckController {

    private final RestTemplate restTemplate;

    public DeckController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private DeckService deckService;

    @PostMapping("/create")
    public ResponseEntity<Deck> createDeck(@RequestParam String commanderId) {
        Deck newDeck = null;
        if (deckService.validateCommander(commanderId)) {
            Card commander = deckService.getCommander(commanderId);
            newDeck = deckService.createDeck(commander);
        }
        return ResponseEntity.ok(newDeck);
    }
}