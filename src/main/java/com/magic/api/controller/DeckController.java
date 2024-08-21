package com.magic.api.controller;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import com.magic.api.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;


    @PostMapping("/create")
    public ResponseEntity<?> createDeck(@RequestParam String commanderId) {
        if (commanderId == null || commanderId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Commander ID cannot be empty.");
        }

        if (!deckService.validateCommander(commanderId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid commander.");
        }

        Card commander = deckService.getCommander(commanderId);
        Deck newDeck = deckService.createDeck(commander);

        return ResponseEntity.ok(newDeck);
    }

    @PostMapping("/add-card")
    public ResponseEntity<?> addCardOnDeck(@RequestParam String cardId, @RequestParam String deckId) {

        if (cardId == null || cardId.trim().isEmpty() || deckId == null || deckId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Card ID and Deck ID cannot be empty.");
        }

        Card card = deckService.fetchCard(cardId);
        Deck deck = deckService.fetchDeck(deckId);

        if (!deckService.validateCommonCard(cardId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid card.");
        }

        if (!deckService.validateColorCardOnDeck(card, deck)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid color type for this deck.");
        }



        if (card == null || deck == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card or Deck not found.");
        }

        Deck updatedDeck = deckService.addCardOnDeck(card, deck);
        return ResponseEntity.ok(updatedDeck);
    }
}
