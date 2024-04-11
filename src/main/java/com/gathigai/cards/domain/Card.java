package com.gathigai.cards.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gathigai.cards.datatypes.CardStatus;
import com.gathigai.cards.utils.validator.Color;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "cards")
@SQLRestriction(value = "deleted=false")
public class Card extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty(message = "Sorry, card name is required")
    private String name;

    @Color
    private String color;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private CardStatus status = CardStatus.TODO;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;
}
