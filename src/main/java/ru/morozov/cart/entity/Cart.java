package ru.morozov.cart.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @SequenceGenerator(name="cart_gen", sequenceName="cart_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="cart_gen")
    private Long id;

    private Long userId;

    @OneToMany(mappedBy="cart", cascade = CascadeType.ALL)
    private List<CartProduct> products = new ArrayList<>();
}
