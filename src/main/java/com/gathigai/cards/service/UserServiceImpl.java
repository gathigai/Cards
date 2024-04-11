package com.gathigai.cards.service;

import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.User;
import com.gathigai.cards.repository.UserRepository;
import com.gathigai.cards.utils.web.errors.BadRequestException;
import com.gathigai.cards.utils.web.errors.NotFoundRestApiException;
import com.gathigai.cards.utils.web.errors.ForbiddenRestApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user, String name) {
        userRepository.findByEmail(user.getEmail()).ifPresent(user1 -> {
            throw new BadRequestException("Sorry, User exists");
        });

        userRepository.findByEmail(name).orElseThrow(() -> new ForbiddenRestApiException("Sorry, forbidden operation."));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String userEmail) {
        return validate(userEmail);
    }

    @Override
    public User findByPublicId(UUID userPublicId) {
        return validateByPublicId(userPublicId);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User update(User user, UUID userPublicId) {
        User savedUser = validateByPublicId(userPublicId);

        if (!user.getId().equals(savedUser.getId())){
            throw new ForbiddenRestApiException("Sorry, operation forbidden.");
        }

        savedUser.setFirstName(user.getFirstName());
        savedUser.setSecondName(user.getSecondName());

        if (user.getRole() != null){
            savedUser.setRole(user.getRole());
        }

        return userRepository.save(savedUser);
    }

    @Override
    public void delete(UUID userPublicId) {

    }

    private User validate(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundRestApiException("Sorry, no user by email: " + userEmail + " found."));

    }

    private User validateByPublicId(UUID publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(() -> new NotFoundRestApiException("Sorry, no user by id: " + publicId + " found."));

    }
}
