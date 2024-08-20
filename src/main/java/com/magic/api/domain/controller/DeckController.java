package com.magic.api.domain.controller;

import com.magic.api.domain.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cards")
public class DeckController {


        @Autowired
        private final RestTemplate restTemplate;

        public DeckController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }


        @GetMapping("/is-commander/{id}")
        public Boolean isCommander(@PathVariable String id) {
            try {
                String url = "https://api.scryfall.com/cards/" + id;
                ResponseEntity<CardResponse> response = restTemplate.getForEntity(url, CardResponse.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    CardResponse card = response.getBody();
                    if (card != null && card.getLegalities() != null) {
                        return "commander".equals(card.getLegalities().get("commander"));
                    }
                }

                return false;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found", e);
            }
        }

}