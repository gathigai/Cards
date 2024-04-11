package com.gathigai.cards.domain.dto;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class CardPaginationCriteria {

    private String page = "0";
    private String size ="10";
    private String offset;
    private String limit;

    public int getPage(){
        return (offset == null || offset.isEmpty()) ? Integer.parseInt(page) : Integer.parseInt(offset);
    }

    public int getSize(){
        return (limit == null || limit.isEmpty()) ? Integer.parseInt(size) : Integer.parseInt(limit);
    }
}
