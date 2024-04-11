package com.gathigai.cards.utils.runner;

import com.gathigai.cards.datatypes.UserRole;
import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.User;
import com.gathigai.cards.repository.UserRepository;
import com.gathigai.cards.service.CardService;
import com.gathigai.cards.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializationRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(UserInitializationRunner.class);

    private final CardService cardService;
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializationRunner(CardService cardService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cardService = cardService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("admin@skyline.com");
        user.setPassword("password");
        user.setFirstName("Admin");
        user.setSecondName("Skyline");
        user.setRole(UserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User user1 = new User();
        user1.setEmail("member@skyline.com");
        user1.setPassword("password");
        user1.setFirstName("Member");
        user1.setSecondName("Skyline");
        user1.setRole(UserRole.MEMBER);
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));

        logger.info("User: " + user);
        logger.info("User1: " + user1);

        User user2 = userRepository.save(user);
        User user3 = userRepository.save(user1);

        logger.info("User2: " + user2);
        logger.info("User3: " + user3);

        Card card = new Card();
        card.setName("ccc");
        card.setColor("#AABBCC");

        Card card1 = new Card();
        card1.setName("aaa");
        card1.setColor("#AABBAA");

        Card card2 = new Card();
        card2.setName("bbb");
        card2.setColor("#AABBBB");

        Card card3 = new Card();
        card3.setName("ddd");
        card3.setColor("#AABBDD");

        cardService.createCard(card, user2.getEmail());
        cardService.createCard(card1, user2.getEmail());
        cardService.createCard(card2, user2.getEmail());
        cardService.createCard(card3, user3.getEmail());
    }
}
