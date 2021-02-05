package ru.morozov.cart.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.cart.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findOneByUserId(Long userId);
}
