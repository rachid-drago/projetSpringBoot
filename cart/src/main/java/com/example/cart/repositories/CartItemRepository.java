package com.example.cart.repositories;

import com.example.cart.domain.Cart;
import com.example.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
