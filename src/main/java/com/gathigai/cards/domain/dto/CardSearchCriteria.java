package com.gathigai.cards.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardSearchCriteria {
    private String name;
    private String color;
    private String status;
    private String createDate;
}
