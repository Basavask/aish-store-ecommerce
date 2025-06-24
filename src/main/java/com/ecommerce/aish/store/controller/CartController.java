package com.ecommerce.aish.store.controller;


import com.ecommerce.aish.store.model.Cart;
import com.ecommerce.aish.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    private String getCurrentUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        Cart cart = cartService.getCartByUser(getCurrentUserEmail(authentication));
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(Authentication authentication, @RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        Cart cart = cartService.addProductToCart(getCurrentUserEmail(authentication), productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItem(Authentication authentication, @RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        Cart cart = cartService.updateCartItemQuantity(getCurrentUserEmail(authentication), productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(Authentication authentication, @PathVariable Long productId) {
        Cart cart = cartService.removeProductFromCart(getCurrentUserEmail(authentication), productId);
        return ResponseEntity.ok(cart);
    }
}