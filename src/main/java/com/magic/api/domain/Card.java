package com.magic.api.domain;


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

    @JsonProperty("object")
    private String object;

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("oracle_id")
    private String oracleId;

    @JsonProperty("multiverse_ids")
    private List<Integer> multiverseIds;

    @JsonProperty("arena_id")
    private Integer arenaId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("released_at")
    private String releasedAt;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("scryfall_uri")
    private String scryfallUri;

    @JsonProperty("layout")
    private String layout;

    @JsonProperty("highres_image")
    private Boolean highresImage;

    @JsonProperty("image_status")
    private String imageStatus;

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

    @JsonProperty("color_identity")
    private List<String> colorIdentity;

    @JsonProperty("keywords")
    private List<String> keywords;



    @JsonProperty("legalities")
    private Map<String, String> legalities;

    @JsonProperty("games")
    private List<String> games;

    @JsonProperty("reserved")
    private Boolean reserved;

    @JsonProperty("foil")
    private Boolean foil;

    @JsonProperty("nonfoil")
    private Boolean nonfoil;

    @JsonProperty("finishes")
    private List<String> finishes;

    @JsonProperty("oversized")
    private Boolean oversized;

    @JsonProperty("promo")
    private Boolean promo;

    @JsonProperty("reprint")
    private Boolean reprint;

    @JsonProperty("variation")
    private Boolean variation;

    @JsonProperty("set_id")
    private String setId;

    @JsonProperty("set")
    private String set;

    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("set_type")
    private String setType;

    @JsonProperty("set_uri")
    private String setUri;

    @JsonProperty("scryfall_set_uri")
    private String scryfallSetUri;

    @JsonProperty("rulings_uri")
    private String rulingsUri;

    @JsonProperty("prints_search_uri")
    private String printsSearchUri;

    @JsonProperty("collector_number")
    private String collectorNumber;

    @JsonProperty("digital")
    private Boolean digital;

    @JsonProperty("rarity")
    private String rarity;

    @JsonProperty("card_back_id")
    private String cardBackId;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("artist_ids")
    private List<String> artistIds;

    @JsonProperty("illustration_id")
    private String illustrationId;

    @JsonProperty("border_color")
    private String borderColor;

    @JsonProperty("frame")
    private String frame;

    @JsonProperty("security_stamp")
    private String securityStamp;

    @JsonProperty("full_art")
    private Boolean fullArt;

    @JsonProperty("textless")
    private Boolean textless;

    @JsonProperty("booster")
    private Boolean booster;

    @JsonProperty("story_spotlight")
    private Boolean storySpotlight;

    @JsonProperty("promo_types")
    private List<String> promoTypes;

    @JsonProperty("prices")
    private Map<String, String> prices;



}