package ru.morozov.cart.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
