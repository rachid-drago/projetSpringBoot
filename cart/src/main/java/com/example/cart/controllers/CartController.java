package com.example.cart.controllers;


import com.example.cart.domain.Cart;
import com.example.cart.domain.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;


   // @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/cart")
    public List<Cart> list() {
        return  cartRepository.findAll();
    }


    @GetMapping("/cart/{id}")
    public Optional<Cart> get(@PathVariable Long id) {
        Optional<Cart> cartInstance = cartRepository.findById(id);
        if (!cartInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Specified cart doesn't exist");
        return cartInstance;

    }

    /*@PostMapping(value = "/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData)
    {
        Cart cart = cartRepository.save(new Cart());
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }*/

    @PostMapping(value = "/cart")
    public  ResponseEntity<Cart> createNewCart(){
        Cart cart = cartRepository.save(new Cart());
        if (cart.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    /*@PatchMapping(value = "/cart/{id}")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");
        cart.get().addProduct(cartItem);
        cartRepository.save(cart.get());
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }*/

    @PostMapping ("/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart (@PathVariable Long id, @RequestBody CartItem cartItem) {

        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        AtomicReference<CartItem> tci = new AtomicReference<CartItem>();
        cart.get().getProducts().forEach((CartItem ci) -> {
            if (ci != null && (ci.getProductId() == cartItem.getProductId())) {
                tci.set(ci);
                return;
            }
        });

        if (tci.get() != null) {
            tci.get().setQuantity(tci.get().getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(tci.get());
            return new ResponseEntity<CartItem>(tci.get(), HttpStatus.CREATED);
        }

        cart.get().addProduct(cartItem);
        cartRepository.save(cart.get());
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }

    @PostMapping(value = "/cartitem/create")
    public ResponseEntity<CartItem> createNewCartItem(@RequestBody CartItem cartItem){
        cartItemRepository.save(cartItem);
        if (cartItem.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cartitem");
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }




}
