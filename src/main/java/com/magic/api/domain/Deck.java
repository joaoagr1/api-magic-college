package com.magic.api.domain;

import com.magic.api.controller.enums.Color;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deckId;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Color> colors;

    @ManyToOne
    @JoinColumn(name = "commander_id")
    private Card commander;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "deck_cards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards = new ArrayList<>();


    public Deck(Card commander) {

        this.commander = commander;
        this.colors = commander.getColors();

    }
}
