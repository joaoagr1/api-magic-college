package com.magic.api.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.rmi.server.UID;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UID id;

    private Card commander;

    private List<Card> cards;

    private List<Color> colors;

    public Deck(Card commander) {

        this.commander = commander;
        this.colors = commander.getColors();

    }
}
