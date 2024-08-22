package com.magic.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {

    @Id
    @JsonProperty("id")
    private String cardId;

    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type_line")
    private String typeLine;

    @Lob
    @JsonProperty("oracle_text")
    private String oracleText;


    @JsonProperty("released_at")
    private String releasedAt;

    @JsonProperty("mana_cost")
    private String manaCost;

    @JsonProperty("cmc")
    private Double cmc;

    @JsonProperty("power")
    private String power;

    @JsonProperty("toughness")
    private String toughness;

    @Transient
    @JsonIgnore
    private Map<String, String> legalities;


    @JsonProperty("rarity")
    private String rarity;


    @JsonProperty("colors")
    @ElementCollection(targetClass = Color.class)
    @CollectionTable(name = "card_colors", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private List<Color> colors;

    @JsonProperty("legalities")
    public void setLegalities(Map<String, String> legalities) {
        this.legalities = legalities;
    }
    @JsonProperty("legalities")
    public Map<String, String> getLegalities() {
        return legalities;
    }

}