package com.magic.api;

import com.magic.api.domain.Card;
import com.magic.api.domain.Deck;
import com.magic.api.domain.User;
import com.magic.api.enums.Color;
import com.magic.api.repository.CardRepository;
import com.magic.api.repository.DeckRespository;
import com.magic.api.service.DeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class DeckServiceTest {

	@Mock
	private DeckRespository deckRepository;

	@Mock
	private CardRepository cardRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private DeckService deckService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFetchCard() {
		// Arrange
		String cardId = "testCardId";
		Card card = new Card();
		when(restTemplate.getForObject(anyString(), eq(Card.class))).thenReturn(card);

		// Act
		Card result = deckService.fetchCard(cardId);

		// Assert
		assertNotNull(result);
		verify(restTemplate, times(1)).getForObject(anyString(), eq(Card.class));
	}

	@Test
	void testValidateCardAsCommander() {
		// Arrange
		String commanderId = "testCommanderId";
		Card card = new Card();
		card.setLegalities(Map.of("commander", "legal"));
		card.setTypeLine("Legendary Creature");
		when(deckService.fetchCard(commanderId)).thenReturn(card);

		// Act
		boolean result = deckService.validateCardAsCommander(commanderId);

		// Assert
		assertTrue(result);
	}

	@Test
	void testCreateDeck() {
		// Arrange
		Card commander = new Card(
				"396f9198-67b6-45d8-91b4-dc853bff9623",
				"testSetName",
				"testName",
				"testTypeLine",
				"testOracleText",
				"testReleasedAt",
				"testManaCost",
				1.0,
				"testPower",
				"testToughness",
				null,
				"testRarity",
				List.of(Color.W)
		);
		commander.setColors(List.of(Color.W));  // Garante que o commander tenha cores definidas

		User user = new User();
		Deck deck = new Deck(commander);
		//deck.setCommander(commander);

		when(cardRepository.findById(anyString())).thenReturn(Optional.of(commander));
		when(deckRepository.save(any(Deck.class))).thenReturn(deck);

		// Act
		Deck result = deckService.createDeck(commander, user);

		// Assert
		assertNotNull(result);
		verify(deckRepository, times(1)).save(any(Deck.class));
	}

}
