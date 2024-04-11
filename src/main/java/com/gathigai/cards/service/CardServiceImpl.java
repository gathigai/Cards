package com.gathigai.cards.service;

import com.gathigai.cards.datatypes.UserRole;
import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.User;
import com.gathigai.cards.domain.dto.CardSearchCriteria;
import com.gathigai.cards.repository.CardRepository;
import com.gathigai.cards.utils.search.CardSpecification;
import com.gathigai.cards.utils.web.errors.NotFoundRestApiException;
import com.gathigai.cards.utils.web.errors.ForbiddenRestApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserService userService;

    public CardServiceImpl(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    @Override
    public Card createCard(Card card, String userEmail) {
        User user = userService.findByEmail(userEmail);
        card.setUser(user);
        return cardRepository.save(card);
    }

    @Override
    public Card findByPublicId(UUID cardPublicId, String userName) {
        User user = userService.findByEmail(userName);
        if (UserRole.ADMIN.equals(user.getRole())){
            return validate(cardPublicId);
        }
        return validate(cardPublicId, userName);
    }

    @Override
    public Page<Card> findAll(String userName, CardSearchCriteria searchCriteria, Pageable pageable) {
        User user = userService.findByEmail(userName);
        return cardRepository.findAll(
                CardSpecification.isName(searchCriteria.getName())
                        .and(CardSpecification.isColor(searchCriteria.getColor()))
                        .and(CardSpecification.isStatus(searchCriteria.getStatus()))
//                        .and(CardSpecification.isGreaterCreationDate(searchCriteria.getCreateDate()))
                        .and(CardSpecification.isLessCreationDate(searchCriteria.getCreateDate()))
                        .and(CardSpecification.isUser(UserRole.ADMIN.equals(user.getRole()) ? null : user))
                , pageable);

    }

    @Override
    public Card update(Card card, UUID cardPublicId, String userName) {
        User user = userService.findByEmail(userName);
        Card savedCard = validate(cardPublicId);

        if (!user.getId().equals(savedCard.getUser().getId())){
            throw new ForbiddenRestApiException("Sorry, access to card denied.");
        }
        savedCard.setName(card.getName());
        savedCard.setColor(card.getColor());
        savedCard.setDescription(card.getDescription());
        if (card.getStatus() != null){
            savedCard.setStatus(card.getStatus());
        }

        return cardRepository.save(savedCard);
    }

    @Override
    public void delete(UUID cardPublicId, String userName) {
        User user = userService.findByEmail(userName);

        cardRepository.findByPublicId(cardPublicId).ifPresent(card -> {
            if (card.getUser().getId().equals(user.getId())) {
                card.setDeleted(true);
                cardRepository.save(card);
            } else {
                throw new ForbiddenRestApiException("Sorry, access to card denied.");
            }
        });
    }

    private Card validate(UUID cardPublicId){
        return cardRepository.findByPublicId(cardPublicId).orElseThrow(() -> new NotFoundRestApiException("Sorry, no card by Id: " + cardPublicId + " found."));
    }
    private Card validate(UUID cardPublicId, String userName){
        return cardRepository.findByPublicIdAndUser_Email(cardPublicId, userName).orElseThrow(() -> new NotFoundRestApiException("Sorry, no card by Id: " + cardPublicId + " found."));
    }
}
