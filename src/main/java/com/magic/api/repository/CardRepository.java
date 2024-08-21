package com.magic.api.repository;

import com.magic.api.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String>{
}
