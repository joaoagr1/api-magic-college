package com.magic.api.repository;

import com.magic.api.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRespository extends JpaRepository<Deck, String> {
}
