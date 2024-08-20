package com.magic.api.service;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RestController;


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

            if ("legal".equals(commanderLegality)) {
                return true;
            } else {
                return false;
            }
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

            if (response == null) {
                return false;
            }

            return true;
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return false;
        } catch (Exception e) {

            return false;
        }





    }

    public boolean validateCommander(String commanderId) {

        boolean cardExists =  validateCard(commanderId);
        boolean isCommander = validateCardAsCommander(commanderId);

        if (cardExists && isCommander) {
            return true;
        } else {
            return false;
        }
    }
}
