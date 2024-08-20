package com.magic.api.controller;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import com.magic.api.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

@RestController("/deck")
public class DeckController {

    private final RestTemplate restTemplate;

    public DeckController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private DeckService deckService;

    @PostMapping("/create")
    public ResponseEntity<String> createDeck(@RequestParam String commanderId) {


      if (deckService.validateCommander(commanderId)){
          Card commander = deckService.getCommander
        deckService.createDeck(commanderId);

      }





        return ResponseEntity.ok("Deck created");
    }



}
