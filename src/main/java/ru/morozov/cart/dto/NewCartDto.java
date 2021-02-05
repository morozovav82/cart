package ru.morozov.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewCartDto {
    private Long userId;
    private List<NewCartProductDto> products;
}
