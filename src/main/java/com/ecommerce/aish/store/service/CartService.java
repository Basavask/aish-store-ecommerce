package com.ecommerce.aish.store.service;

import com.ecommerce.aish.store.exception.ResourceNotFoundException;
import com.ecommerce.aish.store.model.Cart;
import com.ecommerce.aish.store.model.CartItem;
import com.ecommerce.aish.store.model.Product;
import com.ecommerce.aish.store.model.User;
import com.ecommerce.aish.store.repository.CartRepository;
import com.ecommerce.aish.store.repository.ProductRepository;
import com.ecommerce.aish.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> createCartForUser(user));
    }

    public Cart addProductToCart(String userEmail, Long productId, int quantity) {
        Cart cart = getCartByUser(userEmail);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cart.getItems().add(newCartItem);
        }

        return cartRepository.save(cart);
    }

    public Cart updateCartItemQuantity(String userEmail, Long productId, int quantity) {
        Cart cart = getCartByUser(userEmail);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(String userEmail, Long productId) {
        Cart cart = getCartByUser(userEmail);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    private Cart createCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
}