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

@RestController()
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto create(@RequestBody NewCartDto cart) {
        return cartService.create(cart);
    }

    @GetMapping("/{userId}")
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

    @PutMapping("/{userId}/addProduct")
    public ResponseEntity addProduct(@PathVariable("userId") Long userId, @RequestBody NewCartProductDto cartProduct) {
        try {
            cartService.addProduct(userId, cartProduct);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
