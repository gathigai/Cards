package com.gathigai.cards.utils.search;

import com.gathigai.cards.domain.Card;
import com.gathigai.cards.domain.User;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardSpecification{

    public static Specification<Card> isName(String name){
        return (root, query, builder) -> {
            if (name == null || name.isEmpty()){
                return null;
            }
            return builder.equal(root.get("name").as(String.class), name);
        };
    }

    public static Specification<Card> isColor(String color){
        return (root, query, builder) -> {
            if (color == null || color.isEmpty()){
                return null;
            }
            return builder.equal(root.get("color").as(String.class), color);
        };
    }

    public static Specification<Card> isStatus(String status){
        return (root, query, builder) -> {
            if (status == null || status.isEmpty()){
                return null;
            }
            return builder.equal(root.get("status").as(String.class), status);
        };
    }

    public static Specification<Card> isGreaterCreationDate(String createDate){
        return (root, query, builder) -> {
            if (createDate == null){
                return null;
            }

            LocalDate date = Date.valueOf(createDate).toLocalDate();

            return builder.greaterThan(root.get("createDate").as(LocalDate.class),
                    date.atStartOfDay().toLocalDate());
        };
    }

    public static Specification<Card> isLessCreationDate(String createDate){
        return (root, query, builder) -> {
            if (createDate == null){
                return null;
            }

            LocalDateTime date = Date.valueOf(createDate).toLocalDate().atStartOfDay().plusDays(1);

            return builder.lessThan(root.get("createDate").as(LocalDate.class),
                   date.toLocalDate());
        };
    }

    public static Specification<Card> isUser(User user){
        return (root, query, builder) -> {
            if (user == null){
                return null;
            }
            return builder.equal(root.join("user").get("email"), user.getEmail());
        };
    }
}
