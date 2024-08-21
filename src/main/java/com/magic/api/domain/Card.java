package com.magic.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Card {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("released_at")
    private String releasedAt;

    @JsonProperty("mana_cost")
    private String manaCost;

    @JsonProperty("cmc")
    private Double cmc;

    @JsonProperty("type_line")
    private String typeLine;

    @JsonProperty("oracle_text")
    private String oracleText;

    @JsonProperty("power")
    private String power;

    @JsonProperty("toughness")
    private String toughness;

    @JsonProperty("colors")
    private List<Color> colors;

    @JsonIgnore
    private Map<String, String> legalities;

    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("rulings_uri")
    private String rulingsUri;

    @JsonProperty("rarity")
    private String rarity;
}