package com.ibm.ecommerce.service;

import com.ibm.ecommerce.dao.CartDao;
import com.ibm.ecommerce.dao.ProductDao;
import com.ibm.ecommerce.dao.UserDao;
import com.ibm.ecommerce.entity.Cart;
import com.ibm.ecommerce.entity.Product;
import com.ibm.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 12);

        if (searchKey.equals("")) {
            return (List<Product>) productDao.findAll(pageable);
        } else {
            return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
        }
    }

    public Product getProductDetailsById(Integer productId) {
        return productDao.findById(productId).orElse(null);
    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).orElse(null);
            if (product != null) {
                list.add(product);
            }
            return list;
        } else {
            // we are going to checkout the entire cart
            String username = ""; // Set the username from your authentication mechanism
            User user = userDao.findById(username).orElse(null);

            if (user != null) {
                List<Cart> carts = cartDao.findByUser(user);

                return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
        }
    }
}
