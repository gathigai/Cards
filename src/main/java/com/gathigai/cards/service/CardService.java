package com.gathigai.cards.service;

import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.dto.CardSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CardService {

    Card createCard(Card card, String userName);

    Card findByPublicId(UUID cardPublicId, String userName);

    Page<Card> findAll(String userName, CardSearchCriteria searchCriteria, Pageable pageable);

    Card update(Card card, UUID cardPublicId, String userName);

    void delete(UUID cardPublicId, String userName);
}
