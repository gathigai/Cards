package com.gathigai.cards.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class CardSortCriteria {

    private Sort.Direction direction = Sort.Direction.ASC;
    private String[] sortAttributes = new String[]{};
}
