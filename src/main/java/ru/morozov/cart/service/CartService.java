package ru.morozov.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.morozov.cart.dto.CartDto;
import ru.morozov.cart.dto.NewCartDto;
import ru.morozov.cart.dto.NewCartProductDto;
import ru.morozov.cart.entity.Cart;
import ru.morozov.cart.entity.CartProduct;
import ru.morozov.cart.exceptions.NotFoundException;
import ru.morozov.cart.repo.CartProductRepository;
import ru.morozov.cart.repo.CartRepository;
import ru.morozov.cart.mapper.CartMapper;
import ru.morozov.cart.mapper.CartProductMapper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public CartDto create(NewCartDto cartDto) {
        Assert.notNull(cartDto, "CartDto is null");
        Assert.notNull(cartDto.getUserId(), "UserId is null");
        Assert.notNull(cartDto.getProducts(), "Products is null");
        Assert.isTrue(cartDto.getProducts().size() > 0, "Empty products");

        Optional<Cart> res = cartRepository.findOneByUserId(cartDto.getUserId());
        if (res.isPresent()) {
            throw new IllegalArgumentException("Cart already exists for UserId=" + cartDto.getUserId());
        }

        Cart cart = cartRepository.save(
                CartMapper.convertNewCartDtoToCart(cartDto)
        );
        log.info("cart created: " + cart);

        return CartMapper.convertCartToCartDto(cart);
    }

    public CartDto get(Long userId) {
        Optional<Cart> res = cartRepository.findOneByUserId(userId);
        if (res.isPresent()) {
            return CartMapper.convertCartToCartDto(res.get());
        } else {
            throw new NotFoundException(userId);
        }
    }

    public void changeProduct(Long userId, NewCartProductDto cartProduct) {
        Assert.notNull(userId, "UserId is null");
        Assert.notNull(cartProduct, "Product is null");
        Assert.notNull(cartProduct.getProductId(), "ProductId is null");
        Assert.notNull(cartProduct.getQuantity(), "Quantity is null");
        Assert.isTrue(cartProduct.getQuantity() != 0, "Wrong quantity");

        Optional<Cart> res = cartRepository.findOneByUserId(userId);
        if (res.isPresent()) {
            Cart cart = res.get();

            List<CartProduct> products = cart.getProducts();
            Optional<CartProduct> existsProduct = products.stream().filter(i -> i.getProductId().equals(cartProduct.getProductId())).findFirst();
            if (existsProduct.isPresent()) {
                CartProduct existsCartProduct = existsProduct.get();
                int newQnt = cartProduct.getQuantity() + existsCartProduct.getQuantity();
                if (newQnt > 0) {
                    //change qnt
                    existsCartProduct.setQuantity(newQnt);
                    cartRepository.save(cart);
                } else {
                    //remove product from cart
                    cart.getProducts().remove(existsCartProduct);
                    cartProductRepository.delete(existsCartProduct);
                }
                log.info("cart updated: " + cart);
            } else {
                Assert.isTrue(cartProduct.getQuantity() > 0, "Wrong quantity");
                Assert.notNull(cartProduct.getPrice(), "Price is null");
                Assert.isTrue(cartProduct.getPrice() > 0, "Wrong price");

                products.add(CartProductMapper.convertNewCartProductDtoToCartProduct(cartProduct, cart));
                cartRepository.save(cart);
                log.info("cart updated: " + cart);
            }
        } else {
            throw new NotFoundException(userId);
        }
    }

    public void clear(Long orderId, Long userId) {
        Assert.notNull(userId, "UserId is null");

        Optional<Cart> res = cartRepository.findOneByUserId(userId);
        if (res.isPresent()) {
            Cart cart = res.get();

            cartRepository.delete(cart);
            log.info("cart deleted. UserId=" + userId);
        }
    }
}
