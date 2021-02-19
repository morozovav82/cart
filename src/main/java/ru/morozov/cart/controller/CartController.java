package ru.morozov.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.cart.dto.CartDto;
import ru.morozov.cart.dto.NewCartDto;
import ru.morozov.cart.dto.NewCartProductDto;
import ru.morozov.cart.exceptions.NotFoundException;
import ru.morozov.cart.service.CartService;
import ru.morozov.cart.utils.AuthUtils;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> create(@RequestBody NewCartDto cart) {
        if (!AuthUtils.getCurrentUserId().equals(cart.getUserId())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(cartService.create(cart), HttpStatus.CREATED);
    }

    @GetMapping("/{userId:\\d+}")
    public ResponseEntity<CartDto> get(@PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity(
                    cartService.get(userId),
                    HttpStatus.OK
            );
        } catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId:\\d+}/changeProduct")
    public ResponseEntity changeProduct(@PathVariable("userId") Long userId, @RequestBody NewCartProductDto cartProduct) {
        try {
            cartService.changeProduct(userId, cartProduct);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
