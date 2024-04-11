package com.gathigai.cards.web;

import com.gathigai.cards.domain.User;
import com.gathigai.cards.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, Authentication authentication){
        return new ResponseEntity<>(userService.createUser(user, authentication.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{userPublicId}")
    public User fetchUser(@PathVariable("userPublicId") UUID userPublicId, Authentication authentication){
        return userService.findByPublicId(userPublicId);
    }

    @GetMapping
    public Page<User> fetchAll(@PageableDefault Pageable pageable, Authentication authentication) {
        return userService.findAll(pageable);
    }

    @PatchMapping("/{userPublicId}")
    public User updateUser(@PathVariable("userPublicId") UUID userPublicId, @Valid @RequestBody User user, Authentication authentication){
        return userService.update(user, userPublicId);
    }

    @DeleteMapping("/{userPublicId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userPublicId") UUID userPublicId, Authentication authentication){
        try {
            userService.delete(userPublicId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
