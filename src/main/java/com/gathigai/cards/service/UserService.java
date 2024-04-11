package com.gathigai.cards.service;

import com.gathigai.cards.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    User createUser(User user, String name);
    User findByEmail(String userEmail);

    User findByPublicId(UUID userPublicId);

    Page<User> findAll(Pageable pageable);

    User update(User user, UUID userPublicId);

    void delete(UUID userPublicId);
}
