package com.gathigai.cards.repository;

import com.gathigai.cards.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Optional<Card> findByPublicId(UUID cardPublicId);
    Optional<Card> findByPublicIdAndUser_Email(UUID cardPublicId, String userEmail);
}
