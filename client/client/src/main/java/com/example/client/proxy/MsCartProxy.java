package com.example.client.proxy;

import com.example.client.bean.CartBean;
import com.example.client.bean.CartItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "cart", url = "localhost:8092")
public interface MsCartProxy {
    @PostMapping(value = "/cart")
    public ResponseEntity<CartBean> createNewCart();

    @GetMapping(value = "/cart/{id}")
    public Optional<CartBean> getCart(@PathVariable Long id);

    @PostMapping(value = "/cart/{id}")
    public ResponseEntity<CartItemBean> addProductToCart(@PathVariable Long id, @RequestBody CartItemBean cartItem);

    @PostMapping(value = "/cartitem/create")
    public ResponseEntity<CartItemBean> createNewCartItem(@RequestBody CartItemBean cartItem);
}
