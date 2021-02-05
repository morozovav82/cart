package ru.morozov.cart.mapper;

import ru.morozov.cart.dto.CartProductDto;
import ru.morozov.cart.dto.NewCartProductDto;
import ru.morozov.cart.entity.Cart;
import ru.morozov.cart.entity.CartProduct;

public class CartProductMapper {

    public static CartProductDto convertCartProductToCartProductDto(CartProduct cartProduct) {
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setId(cartProduct.getId());
        cartProductDto.setProductId(cartProduct.getProductId());
        cartProductDto.setQuantity(cartProduct.getQuantity());
        cartProductDto.setPrice(cartProduct.getPrice());

        return cartProductDto;
    }

    public static CartProduct convertNewCartProductDtoToCartProduct(NewCartProductDto newCartProductDto, Cart cart) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProductId(newCartProductDto.getProductId());
        cartProduct.setQuantity(newCartProductDto.getQuantity());
        cartProduct.setPrice(newCartProductDto.getPrice());

        return cartProduct;
    }
}
