package ru.morozov.cart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewCartProductDto {
    private Long productId;
    private Integer quantity;
    private Float price;
}
