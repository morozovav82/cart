package ru.morozov.cart.mapper;

import org.springframework.util.CollectionUtils;
import ru.morozov.cart.dto.CartDto;
import ru.morozov.cart.dto.NewCartDto;
import ru.morozov.cart.entity.Cart;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartDto convertCartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUserId());

        if (!CollectionUtils.isEmpty(cart.getProducts())) {
            cartDto.setProducts(
                cart.getProducts().stream().map(i -> CartProductMapper.convertCartProductToCartProductDto(i)).collect(Collectors.toList())
            );
        }

        return cartDto;
    }

    public static Cart convertNewCartDtoToCart(NewCartDto newCartDto) {
        Cart cart = new Cart();
        cart.setUserId(newCartDto.getUserId());

        if (!CollectionUtils.isEmpty(newCartDto.getProducts())) {
            cart.setProducts(
                    newCartDto.getProducts().stream().map(i -> CartProductMapper.convertNewCartProductDtoToCartProduct(i, cart)).collect(Collectors.toList())
            );
        }

        return cart;
    }
}
