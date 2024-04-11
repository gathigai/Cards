package com.gathigai.cards.domain;

import com.gathigai.cards.datatypes.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Sorry, valid email required.")
    @NotEmpty(message = "Sorry, user email is required.")
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @NotEmpty(message = "Sorry, user password is required.")
    @Column(nullable = false)
    private String password;

    private String firstName;

    private String secondName;

    @NotNull(message = "Sorry, user role is required.")
    @Column(nullable = false)
    private UserRole role;
}
