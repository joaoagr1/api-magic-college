package com.magic.api.domain;

import jakarta.persistence.*;
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
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Card commander;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Color> colors;

    @ManyToMany
    @JoinTable(
            name = "deck_cards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;


    public Deck(Card commander) {

        this.commander = commander;
        this.colors = commander.getColors();

    }
}
