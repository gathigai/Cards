package com.gathigai.cards.web;

import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.dto.CardPaginationCriteria;
import com.gathigai.cards.domain.dto.CardSearchCriteria;
import com.gathigai.cards.domain.dto.CardSortCriteria;
import com.gathigai.cards.service.CardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardRestController {

    private final CardService cardService;

    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, Authentication authentication){
        return new ResponseEntity<>(cardService.createCard(card, authentication.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{cardPublicId}")
    public Card fetchCard(@PathVariable("cardPublicId") UUID cardPublicId, Authentication authentication){
        return cardService.findByPublicId(cardPublicId, authentication.getName());
    }

    @GetMapping
    public Page<Card> fetchAll(CardSearchCriteria searchCriteria, CardPaginationCriteria paginationCriteria,
                               CardSortCriteria sortCriteria, Authentication authentication) {
        Pageable page;
        if (sortCriteria.getSortAttributes().length > 0){
            page = PageRequest.of(paginationCriteria.getPage(), paginationCriteria.getSize(),
                    Sort.by(sortCriteria.getDirection(), sortCriteria.getSortAttributes()));
        } else {
            page = PageRequest.of(paginationCriteria.getPage(), paginationCriteria.getSize());
        }

        return cardService.findAll(authentication.getName(), searchCriteria, page);
    }

    @PatchMapping("/{cardPublicId}")
    public Card updateCard(@PathVariable("cardPublicId") UUID cardPublicId, @Valid @RequestBody Card card, Authentication authentication){
        return cardService.update(card, cardPublicId, authentication.getName());
    }

    @DeleteMapping("/{cardPublicId}")
    public ResponseEntity<Void> deleteCard(@PathVariable("cardPublicId") UUID cardPublicId, Authentication authentication){
        try {
            cardService.delete(cardPublicId, authentication.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
