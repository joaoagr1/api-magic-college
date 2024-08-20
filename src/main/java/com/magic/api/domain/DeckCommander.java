package com.magic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeckCommander {

    private Commander commander;

    private Collors collors;

    private List<Card> cards;



}
