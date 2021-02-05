package ru.morozov.cart.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "cart_products")
@Getter
@Setter
@ToString(exclude = "cart")
public class CartProduct {

    @Id
    @SequenceGenerator(name="cart_products_gen", sequenceName="cart_products_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="cart_products_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    private Long productId;
    private Integer quantity;
    private Float price;
}
