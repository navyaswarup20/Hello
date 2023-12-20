package com.ibm.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.ecommerce.dao.CartDao;
import com.ibm.ecommerce.dao.ProductDao;
import com.ibm.ecommerce.dao.UserDao;
import com.ibm.ecommerce.entity.Cart;
import com.ibm.ecommerce.entity.Product;
import com.ibm.ecommerce.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).orElse(null);

        // Retrieve the current user from Spring Security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findById(username).orElse(null);

        if (product != null && user != null) {
            List<Cart> cartList = cartDao.findByUser(user);
            List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());

            if (filteredList.isEmpty()) {
                Cart cart = new Cart(product, user);
                return cartDao.save(cart);
            }
        }

        return null;
    }

    public List<Cart> getCartDetails() {
        // Retrieve the current user from Spring Security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findById(username).orElse(null);

        if (user != null) {
            return cartDao.findByUser(user);
        } else {
            return null;
        }
    }
}
